show databases;
use test;
create table User(
    uuid binary(16) primary key, #insert时需要UUID_TO_BIN(UUID(),false),取出时相反
    name nvarchar(20) not null,
    password varchar(20) not null,
    gender binary(1), #0女1男
    age int(2),
    mobile varchar(15),
    email varchar(50),
    avatar blob #头像；insert时需LOAD_FILE('/path/to/image/avatar.jpg')
);

create table Chatroom(
    uuid binary(16) primary key,#insert时需要UUID_TO_BIN(UUID())
    owner_uuid binary(16),
    name nvarchar(15) not null,
    userNum tinyint default 1,
    avatar blob, #头像；insert时需LOAD_FILE('/path/to/image/avatar.jpg')
    foreign key (owner_uuid) references User(uuid)
);

create table ChatMembership(
    chatroom_uuid binary(16),
    user_uuid binary(16),
    foreign key (chatroom_uuid) references Chatroom(uuid),
    foreign key (user_uuid) references User(uuid),
    primary key (chatroom_uuid, user_uuid)
);

create table Message(
    id bigint AUTO_INCREMENT primary key ,
    sender binary(16),
    chatroom binary(16),
    date datetime,
    content nvarchar(255),
    foreign key (sender) references User(uuid),
    foreign key (chatroom) references Chatroom(uuid)
);

create table UserLastReadMsg(
    user_uuid binary (16),
    chatroom_uuid binary(16),
    last_read_msg_id bigint,
    foreign key (user_uuid) references User(uuid),
    foreign key (chatroom_uuid) references Chatroom(uuid),
    foreign key (last_read_msg_id) references Message(id),
    primary key (user_uuid,chatroom_uuid)
);

create table LoginSession(
    user_uuid binary(16) primary key,
    token binary(255),
    valid_until datetime,
    foreign key (user_uuid) references User(uuid)
)
        /*----以下为增删查改代码----*/


/* 用户认证 */

    #POST /auth/login

        #Request: select有结果则可以登录
    select * from User
        where User.name = 'your_name' and User.password = 'your_password';

        #Response: Ok则设置 user_token: session_token 和 user_uuid: user_uuid
            #理论上UUID肯定事先存在，获取成为变量方便后面使用
    SET @your_user_uuid = (SELECT User.uuid FROM User WHERE User.name = 'Bruce');

    insert into LoginSession(user_uuid, token, valid_until)
        values(@your_user_uuid,'new_token','datetime')
        on duplicate key update token = 'new_token', valid_until = 'datetime';
            #返回uuid和name
    select bin_to_uuid(User.uuid,false),User.name
        from User where User.uuid = @your_user_uuid;
            #清除变量
    SET @your_user_uuid = NULL;

    #POST /auth/signup

        #Request:
        #生成新uuid并保存至变量
    SET @your_user_uuid = uuid_to_bin('your_uuid',false);
    insert into User(uuid,name,password)
        values(@your_user_uuid,'your_name','password');

        #Response:
    insert into LoginSession(user_uuid, token, valid_until)
        values(@your_user_uuid,'new_token','datetime')
            on duplicate key update token = 'new_token', valid_until = 'datetime';
    #返回uuid和name
    select bin_to_uuid(User.uuid,false),User.name
        from User where User.uuid = @your_user_uuid;
    #清除变量
    SET @your_user_uuid = NULL;


/* 用户管理 */

    #GET /user/[user_uuid]
        #Response
    select bin_to_uuid(User.uuid,false),User.name
        from User where User.uuid = uuid_to_bin('your_uuid',false);


    #PATCH /user/[user_uuid]
        #Request
    SET @your_user_uuid = uuid_to_bin('your_uuid',false);
    update User set User.name = 'new_name', User.password = 'new_pswd'
        where User.uuid = @your_user_uuid
            and User.password = 'auth_pswd';

        #Response
    select bin_to_uuid(User.uuid,false),User.name
        from User where User.uuid = @your_user_uuid;
    SET @your_user_uuid = NULL;


    #DELETE /user/[user_uuid]
    delete from User where User.uuid = 'user_uuid';



/* 聊天室管理 */
    #GET /chat
    select Chatroom.name, Chatroom.uuid, count(Message.id) as UnreadMessages
        from chatmembership join chatroom on ChatMembership.user_uuid = Chatroom.uuid
            left join UserLastReadMsg on ChatMembership.chatroom_uuid = UserLastReadMsg.chatroom_uuid
                and ChatMembership.user_uuid = UserLastReadMsg.user_uuid
            left join Message on ChatMembership.chatroom_uuid = Message.chatroom
        where ChatMembership.user_uuid = uuid_to_bin('your_uuid',false)
            and (UserLastReadMsg.last_read_msg_id is null or Message.id > UserLastReadMsg.last_read_msg_id)
        group by
            Chatroom.name, Chatroom.uuid;

    #GET /chat/[chat_uuid]
        #查询聊天室名称和群主
            #定义chatroom_uuid
    set @chatroom_uuid = uuid_to_bin('chatroom_uuid',false);
    select Chatroom.name, User.name as owner_name
        from Chatroom join User on Chatroom.owner_uuid = User.uuid
            where Chatroom.uuid = @chatroom_uuid;
        #查询群成员
    select User.uuid, User.name
        from User join chatmembership on User.uuid = chatmembership.user_uuid
            where ChatMembership.chatroom_uuid = @chatroom_uuid;
    set @chatroom_uuid = null;

    #PATCH /chat/[chat_id]
    update Chatroom
        set Chatroom.name = 'new_name'
            where Chatroom.uuid = uuid_to_bin('chatroom_uuid',false);

    #DELETE /chat/[chat_id]
        #不仅删除聊天室，还要删除相关的东西
    START TRANSACTION;
    set @chatroom_uuid = UUID_TO_BIN('chatroom_uuid', false);
    -- 删除与聊天室相关的所有消息
    DELETE FROM Message
        WHERE chatroom = @chatroom_uuid;
    -- 删除与聊天室相关的所有聊天关系
    DELETE FROM ChatMembership
        WHERE chatroom_uuid = @chatroom_uuid;
    -- 如果有，删除与聊天室相关的最后阅读消息记录
    DELETE FROM UserLastReadMsg
        WHERE chatroom_uuid = @chatroom_uuid;
    -- 删除聊天室
    DELETE FROM Chatroom
        WHERE uuid = @chatroom_uuid
          AND owner_uuid = UUID_TO_BIN('user_uuid', false);
    -- 删除变量
    set @chatroom_uuid = null;
    -- 尝试提交事务
    COMMIT;


    #POST /chat/[chat_id]/join
    insert ChatMembership(chatroom_uuid, user_uuid)
        values(UUID_TO_BIN('chatroom_uuid', false),UUID_TO_BIN('user_uuid', false));

    #POST /chat/[chat_id]/leave
    delete from ChatMembership
        where ChatMembership.user_uuid = UUID_TO_BIN('user_uuid', false);

    #GET /chat/[chat_id]/msg
    SELECT
        User.name AS sender,
        Message.content,
        YEAR(Message.date) AS year,
        MONTH(Message.date) AS month,
        DAY(Message.date) AS day,
        HOUR(Message.date) AS hour,
        MINUTE(Message.date) AS minute
    FROM
        Message
            JOIN User ON Message.sender = User.uuid
    WHERE
        Message.chatroom = UUID_TO_BIN('chatroom_uuid', false)
    ORDER BY
        Message.date;

/* 搜索处理 */
    #GET /search/user?{query}
    SELECT
        uuid,
        name
    FROM
        User
    WHERE
        name LIKE CONCAT('%', REPLACE('pattern', '%', '\\%'), '%');
            #上述pattern中对通配符进行转义，防止错误通配

    #GET /search/chat?{query}
    select
        uuid,
        Chatroom.name
    from
        Chatroom
    where
        name like CONCAT('%', REPLACE('pattern', '%', '\\%'), '%');


            /*----以下为测试使用代码----*/

select * from User;
select * from Chatroom;
select * from ChatMembership;
select * from Message;
select * from UserLastReadMsg;
select * from LoginSession;

#datetime: 2024-12-31 23:59:59.999999
#uuid: 62ab1547-710f-11e8-9a58-5254007205d6
insert into User(uuid,name,password,gender,age,mobile,email,avatar)
values(uuid_to_bin('62ab1547-710f-11e8-9a58-5254007205d6'),'Bruce','123456',1,19,'18156375507','3573729289@qq.com',null);

SET @your_user_id = (SELECT User.uuid FROM User WHERE User.name = 'Bruce');

insert into LoginSession(user_uuid, token, valid_until)
values(@your_user_id,'new_token','2024-12-31 23:59:59.999999')
on duplicate key update token = 'new_token', valid_until = '2024-12-31 23:59:59.999999';
#返回uuid和name
select bin_to_uuid(User.uuid,false),User.name
from User where User.uuid = @your_user_id;

delete from LoginSession
where LoginSession.user_uuid = uuid_to_bin('62ab1547-710f-11e8-9a58-5254007205d6');

insert into User(uuid,name,password)
values(uuid_to_bin('62ab1547-710f-11e8-9a58-5254007205d7'),'your_name','password');

select Chatroom.name, Chatroom.uuid, count(Message.id) as UnreadMessages
from chatmembership join chatroom on ChatMembership.user_uuid = Chatroom.uuid
                    left join UserLastReadMsg on ChatMembership.chatroom_uuid = UserLastReadMsg.chatroom_uuid
    and ChatMembership.user_uuid = UserLastReadMsg.user_uuid
                    left join Message on ChatMembership.chatroom_uuid = Message.chatroom
where ChatMembership.user_uuid = uuid_to_bin('62ab1547-710f-11e8-9a58-5254007205d7',false)
  and (UserLastReadMsg.last_read_msg_id is null or Message.id > UserLastReadMsg.last_read_msg_id)
group by
    Chatroom.name, Chatroom.uuid;

#DELETE /chat/[chat_id]
#不仅删除聊天室，还要删除相关的东西
START TRANSACTION;
set @chatroom_uuid = UUID_TO_BIN('62ab1547-710f-11e8-9a58-5254007205d6', false);
-- 删除与聊天室相关的所有消息
DELETE FROM Message
WHERE chatroom = @chatroom_uuid;
-- 删除与聊天室相关的所有聊天关系
DELETE FROM ChatMembership
WHERE chatroom_uuid = @chatroom_uuid;
-- 如果有，删除与聊天室相关的最后阅读消息记录
DELETE FROM UserLastReadMsg
WHERE chatroom_uuid = @chatroom_uuid;
-- 删除聊天室
DELETE FROM Chatroom
WHERE uuid = @chatroom_uuid
  AND owner_uuid = UUID_TO_BIN('62ab1547-710f-11e8-9a58-5254007205d6', false);
-- 删除变量
set @chatroom_uuid = null;
-- 尝试提交事务
COMMIT;

#GET /chat/[chat_id]/msg
SELECT
    User.name AS sender,
    Message.content,
    YEAR(Message.date) AS year,
    MONTH(Message.date) AS month,
    DAY(Message.date) AS day,
    HOUR(Message.date) AS hour,
    MINUTE(Message.date) AS minute
FROM
    Message
        JOIN User ON Message.sender = User.uuid
WHERE
    Message.chatroom = UUID_TO_BIN('62ab1547-710f-11e8-9a58-5254007205d6', false)
ORDER BY
    Message.date;

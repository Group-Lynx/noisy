show databases;
use test;
create table User(
    uuid binary(16) primary key, #insert时需要UUID_TO_BIN(UUID()),取出时相反
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
    masterID binary(16),
    name nvarchar(15) not null,
    userNum tinyint default 1,
    avatar blob, #头像；insert时需LOAD_FILE('/path/to/image/avatar.jpg')
    foreign key (masterid) references User(uuid)
);

create table ChatMember(
    ChatroomID binary(16),
    UserID binary(16),
    foreign key (ChatroomID) references Chatroom(uuid),
    foreign key (UserID) references User(uuid),
    primary key (ChatroomID, UserID)
);

create table Message(
    id int AUTO_INCREMENT primary key ,
    sender binary(16),
    chatroom binary(16),
    date datetime,
    content nvarchar(255),
    foreign key (sender) references User(uuid),
    foreign key (chatroom) references Chatroom(uuid)
);

create table Message2User(
    MessageID int,
    receiverID binary(16),
    is_read binary(1) default 0, #0未读1已读
    primary key (MessageID,receiverID),
    foreign key (MessageID) references Message(id),
    foreign key (receiverID) references User(uuid)
);
/*
说明：Notification直接通过Message2User抓取即可
抓取未读状态的message推送给对应的用户
以下为测试代码
*/

select * from User;
select * from Chatroom;
select * from ChatMember;
select * from Message;
select * from Message2User;
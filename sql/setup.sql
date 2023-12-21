show databases;
use test;
create table User(
                     name nvarchar(20) not null unique primary key,
                     password varchar(20) not null,
                     gender binary(1), #0女1男
                     age int(2),
                     mobile varchar(15),
                     email varchar(50),
                     avatar blob #头像；insert时需LOAD_FILE('/path/to/image/avatar.jpg')
);

create table Chatroom(
                     name nvarchar(20) not null unique primary key,
                     owner_name nvarchar(20),
                     userNum tinyint default 1,
                     avatar blob, #头像；insert时需LOAD_FILE('/path/to/image/avatar.jpg')
                     foreign key (owner_name) references User(name)
);

create table ChatMembership(
                   chatroom_name nvarchar(20),
                   user_name nvarchar(20),
                   foreign key (chatroom_name) references Chatroom(name),
                   foreign key (user_name) references User(name),
                   primary key (chatroom_name, user_name)
);

create table Message(
                        id bigint AUTO_INCREMENT primary key ,
                        sender nvarchar(20),
                        chatroom nvarchar(20),
                        date datetime,
                        content nvarchar(255),
                        foreign key (sender) references User(name),
                        foreign key (chatroom) references Chatroom(name)
);

create table UserLastReadMsg(
                    user_name nvarchar(20),
                    chatroom_name nvarchar(20),
                    last_read_msg_id bigint,
                    foreign key (user_name) references User(name),
                    foreign key (chatroom_name) references Chatroom(name),
                    foreign key (last_read_msg_id) references Message(id),
                    primary key (user_name,chatroom_name)
);

create table LoginSession(
                             user_name nvarchar(20) primary key,
                             token binary(255),
                             valid_until datetime,
                             foreign key (user_name) references User(name)
)

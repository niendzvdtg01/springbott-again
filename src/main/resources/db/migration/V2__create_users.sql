create table student(
    student_id int primary key not null auto_increment,
    name nvarchar(225) not null,
    age int,
    email nvarchar(225),
    gender enum('F', 'M')
);

create table projects
(
    id          int primary key auto_increment,
    description varchar2(100) not null,
);

create table project_steps
(
  id         int primary key auto_increment,
  description varchar2(100) not null,
  project_id int not null,
  days_to_deadline int not null,
  foreign key (project_id) references projects (id)
);

alter table task_groups
    add column project_id int not null;
alter table task_groups
    add foreign key (project_id) references projects (id);
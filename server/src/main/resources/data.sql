insert into app_user values('admin',true,'ADMIN','ADMIN');
alter table session alter column expiry type timestamp with time zone using expiry at time zone 'Asia/Kolkata'
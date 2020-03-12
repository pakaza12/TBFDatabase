-- Query to retrieve major fields

-- Query to return the email(s) for a person
select e.email from Email e left join Person p on p.personId = e.personId where p.personCode = '1AB'; -- Replace 11AB with the desired userCode
-- Query to add an email to a person
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '1AB'), 'hsalazar@msn.com'); -- Change the user code that you want to add to then add the email address
-- A query to change the email address of a given email record
update Email set email = 'hsalazar0@gmail.com' where email = 'hsalazar0@apache.org';
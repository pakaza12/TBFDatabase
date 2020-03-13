-- Query to retrieve major fields

-- Query to return the email(s) for a person
select e.email from Email e left join Person p on p.personId = e.personId where p.personCode = '1AB'; -- Replace 11AB with the desired userCode
-- Query to add an email to a person
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '1AB'), 'hsalazar@msn.com'); -- Change the user code that you want to add to then add the email address
-- A query to change the email address of a given email record
update Email set email = 'hsalazar0@gmail.com' where email = 'hsalazar0@apache.org';
-- A query (or series of queries) to remove a given person record

-- TO FIX
-- delete from Email where personId = (select p.personId from Person p where p.personCode = '1AB');
select p.portfolioCode from Person pm left join Portfolio p on p.personId = pm.personId where p.managerCode = '1AB';
delete from AssetPortfolio where portfolioId = (select p.portfolioId from Portfolio p left join Person pm on p.personId = pm.personId where p.ownerCode = '1AB');
delete from Portfolio where personId = (select p.personId from Person p where p.personCode = '1AB');
delete p.managerCode from Portfolio p where p.personId = (select pm.personId from Person pm where p.personCode = '1AB');
delete from Person where personCode = '1AB';

-- A query to create a person record
insert into Person(personCode, addressId, firstName, lastName, brokerStatus, secIdentity) values ('12AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '91421 Karstens Street'), 'Ella', 'Swat', 'E', 'sec005');
-- A query to get all the assets in a particular portfolio
select a.assetCode from Asset a 
	left join AssetPortfolio ap on a.assetId = ap.assetId 
		left join Portfolio p on p.portfolioId = ap.portfolioId 
			where p.portfolioCode = '2CB'; -- Change the 2CB with the portfolioCode you want the assets for
-- A query to get all the assets of a particular person
select a.assetCode from Asset a 
	left join AssetPortfolio ap on a.assetId = ap.assetId 
		left join Portfolio p on p.portfolioId = ap.portfolioId 
			left join Person pr on pr.personId = p.personId 
				where pr.personCode = '2AB'; -- Change 3AB with the personCode you want the assets for
-- A query to create a new asset record
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, baseOmegaMeasure, totalValue) values('17BB', 'Investment Property 1', 20349, 10.2, 0.21, 1250000);
-- A query to create a new portfolio record
insert into Portfolio(portfolioCode, ownerCode, managerCode, personId) values ('10CB', '12AB', '3AB', (select p.personId from Person p where p.personCode = '12AB'));
-- A query to associate a particular asset with a particular portfolio
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '10CB'), 12.5, (select a.assetId from Asset a where a.assetCode = '17BB'));
-- A query to find the total number of portfolios owned by each person
select pm.firstName, pm.lastName, count(p.portfolioId) as numPortfolios from Person pm left join Portfolio p on p.personId = pm.personId group by pm.personCode;
-- A query to find the total number of portfolios managed by each person
select pm.firstName, pm.lastName, count(p.managerCode) as numManaged from Person pm left join Portfolio p on p.managerCode = pm.personCode group by pm.personCode; -- Since we removed 1AB earlier, Adolpho doesn't have any


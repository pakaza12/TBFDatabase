-- Query to retrieve major fields

-- Query to return the email(s) for a person
select e.email from Email e left join Person p on p.personId = e.personId where p.personCode = '1AB'; -- Replace 11AB with the desired userCode
-- Query to add an email to a person
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '1AB'), 'hsalazar@msn.com'); -- Change the user code that you want to add to then add the email address
-- A query to change the email address of a given email record
update Email set email = 'hsalazar0@gmail.com' where email = 'hsalazar0@apache.org';
-- A query (or series of queries) to remove a given person record
delete from Email where personId = (select p.personId from Person p where p.personCode = '1AB');
delete from AssetPortfolio where portfolioId = (select p.portfolioId from Portfolio p left join Person pm on p.personId = pm.personId where p.ownerCode = '1AB');
delete from Portfolio where personId = (select p.personId from Person p where p.personCode = '1AB');
update Portfolio p set p.managerCode = null where p.managerCode = '1AB';
update Portfolio p set p.beneficiaryCode = null where p.beneficiaryCode = '1AB';
delete from Person where personCode = '1AB';
-- A query to create a person record
insert into Person(personCode, addressId, firstName, lastName, brokerStatus, secIdentity) values ('12AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '91421 Karstens Street'), 'Ella', 'Swat', 'E', 'sec005');
-- A query to get all the assets in a particular portfolio
select p.portfolioCode, a.assetCode, a.label from Asset a 
	left join AssetPortfolio ap on a.assetId = ap.assetId 
		left join Portfolio p on p.portfolioId = ap.portfolioId 
			where p.portfolioCode = '2CB'; -- Change the 2CB with the portfolioCode you want the assets for
-- A query to get all the assets of a particular person
select pm.firstName, pm.lastName, a.assetCode from Asset a 
	left join AssetPortfolio ap on a.assetId = ap.assetId 
		left join Portfolio p on p.portfolioId = ap.portfolioId 
			left join Person pm on pm.personId = p.personId 
				where pm.personCode = '2AB'; -- Change 3AB with the personCode you want the assets for
-- A query to create a new asset record
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, baseOmegaMeasure, totalValue) values('17BB', 'Investment Property 1', 20349, 10.2, 0.21, 1250000);
-- A query to create a new portfolio record
insert into Portfolio(portfolioCode, ownerCode, managerCode, personId) values ('10CB', '12AB', '3AB', (select p.personId from Person p where p.personCode = '12AB'));
-- A query to associate a particular asset with a particular portfolio
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '10CB'), 125, (select a.assetId from Asset a where a.assetCode = '17BB'));
-- A query to find the total number of portfolios owned by each person
select pm.firstName, pm.lastName, count(p.portfolioId) as numPortfolios from Person pm left join Portfolio p on p.personId = pm.personId group by pm.personCode;
-- A query to find the total number of portfolios managed by each person
select pm.firstName, pm.lastName, count(p.managerCode) as numManaged from Person pm left join Portfolio p on p.managerCode = pm.personCode group by pm.personCode; -- Since we removed 1AB earlier, Adolpho doesn't have any
--  A query to find the total value of all stocks in each portfolio 
select p.portfolioCode, sum(ap.assetValue * a.sharePrice) as stockValue from Portfolio p left join AssetPortfolio ap on p.portfolioId = ap.portfolioId left join Asset a on ap.assetId = a.assetId group by p.portfolioCode;
-- A query to detect an invalid distribution of private investment assets
select ap.assetId, ap.assetValue as invalidAsset from AssetPortfolio ap left join Asset a on ap.assetId = a.assetId where a.baseOmegaMeasure != null group by ap.assetId;

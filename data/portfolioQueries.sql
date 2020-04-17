-- Jayden Carlon and Parker Zach
-- Queries for portfolioDB

select pm.personId, pm.personCode, pm.firstName, pm.lastName, pm.brokerStatus as brokerStatus, pm.secIdentity as secIdentity, ad.street, c.city, s.state, ad.zip, ad.country from Person pm 
	                left join Address ad on pm.addressId = ad.addressId
                    left join City c on ad.cityId = c.cityId
                    left join State s on ad.stateId = s.stateId;


-- Query to retrieve major fields for Portfolio
select p.portfolioId, p.portfolioCode, p.ownerCode, p.managerCode, p.beneficiaryCode, a.assetCode, ap.assetValue from Portfolio p left join AssetPortfolio ap on ap.portfolioId = p.portfolioId
	left join Asset a on a.assetId = ap.assetId;
    
select a.assetCode, a.label, a.apr, a.quarterlyDividend, a.baseRateReturn, a.betaMeasure, a.stockSymbol, a.sharePrice, a.baseOmegaMeasure, a.totalValue from Asset a;
-- Query to retrieve major fields for Assets
select a.assetCode, a.label, a.apr, a.quarterlyDividend, a.baseRateReturn, a.betaMeasure, a.stockSymbol, a.sharePrice, a.baseOmegaMeasure, a.totalValue, ap.assetValue from Asset a
	left join AssetPortfolio ap on ap.assetId = a.assetId;

-- Query to retrieve major fields
select pm.personCode, pm.firstName, pm.lastName, pm.brokerStatus as brokerStatus, pm.secIdentity as secIdentity, ad.street, c.city, s.state, ad.zip, ad.country, e.email as 'email(s)' from Person pm 
	left join Address ad on pm.addressId = ad.addressId 
		left join City c on ad.cityId = c.cityId
			left join State s on ad.stateId = s.stateId
				left join Email e on e.personId = pm.personId; 
					-- 'coalesce' replaces null with an empty string for output, purely aesthetic for now
                
-- Query to return the email(s) for a person
select e.email from Email e left join Person p on p.personId = e.personId where p.personCode = '5AB';
	-- Replace 1AB with the desired userCode

-- Query to add an email to a person
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '1AB'), 'hsalazar@msn.com'); 
	-- Change the userCode that you want to add to then add the email address

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
			where p.portfolioCode = '2CB'; 
				-- Change the 2CB with the portfolioCode you want the assets for
                
-- A query to get all the assets of a particular person
select pm.firstName, pm.lastName, a.assetCode from Asset a 
	left join AssetPortfolio ap on a.assetId = ap.assetId 
		left join Portfolio p on p.portfolioId = ap.portfolioId 
			left join Person pm on pm.personId = p.personId 
				where pm.personCode = '2AB'; 
					-- Change 3AB with the personCode you want the assets for

select * from Address;
select * from Person;
insert into Person(personCode, addressId, firstName, lastName) values ('100A', (Select a.addressId from Address a left join City c on  a.cityId = c.cityId left join State s on s.stateId = a.stateId where (a.street = '00 Veith Center' AND a.zip = 77015 AND a.country = 'US') ), 'Parker', 'Zach');
select secIdentity from Person;
select state from State;
select s.street from Address s;
select p.firstName from Person p where p.personCode = "1AB";
select a.street, a.country, a.zip from Address a left join State s on s.stateId = a.stateId left join City c on c.cityId = a.cityId where a.street = "10 Acker Court";
select c.city from City c where c.city = "Hust";

-- A query to create a new asset record
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, baseOmegaMeasure, totalValue) values('17BB', 'Investment Property 1', 20349, 10.2, 0.21, 1250000);

-- A query to create a new portfolio record
insert into Portfolio(portfolioCode, ownerCode, managerCode, personId) values ('10CB', '12AB', '3AB', (select p.personId from Person p where p.personCode = '12AB'));

-- A query to associate a particular asset with a particular portfolio
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '10CB'), 12.5, (select a.assetId from Asset a where a.assetCode = '17BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '7CB'), 92.5, (select a.assetId from Asset a where a.assetCode = '17BB'));

-- A query to find the total number of portfolios owned by each person
select pm.firstName, pm.lastName, count(p.portfolioId) as numPortfolios from Person pm left join Portfolio p on p.personId = pm.personId group by pm.personCode;

-- A query to find the total number of portfolios managed by each person
select pm.firstName, pm.lastName, count(p.managerCode) as numManaged from Person pm left join Portfolio p on p.managerCode = pm.personCode group by pm.personCode; 
	-- Since we removed 1AB earlier, Adolpho doesn't have any
    
--  A query to find the total value of all stocks in each portfolio 
select p.portfolioCode, round(coalesce(sum(ap.assetValue * a.sharePrice), 0), 2) as stockValue from Portfolio p -- 'coalesce' makes null value equal to 0 in this case, 'round' rounds to 2 decimals in this case
	left join AssetPortfolio ap on p.portfolioId = ap.portfolioId 
		left join Asset a on ap.assetId = a.assetId group by p.portfolioCode;
        
-- A query to detect an invalid distribution of private investment assets
select a.assetCode, a.label, sum(ap.assetValue) as StakePercentage from AssetPortfolio ap
	left join Asset a on ap.assetId = a.assetId 
		where a.baseOmegaMeasure > 0 group by assetCode having StakePercentage > 100; 
        -- the 'having' keyword doesn't test the condition until after the sum is calculated
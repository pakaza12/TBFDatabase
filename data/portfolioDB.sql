--# Assignment 4
--# show tables;
drop table if exists AssetPortfolio;
drop table if exists Asset;
drop table if exists Portfolio;
drop table if exists Email;
drop table if exists Person;
drop table if exists Address;
drop table if exists City;
drop table if exists State;

create table City (
	cityId int not null primary key auto_increment,
	city varchar(255) not null
    
)engine=InnoDB,collate=latin1_general_cs;

create table State (
	stateId int not null primary key auto_increment,
	state varchar(255) not null
    
)engine=InnoDB,collate=latin1_general_cs;

create table Address (
	addressId int not null primary key auto_increment,
	street varchar(255) not null,
    zip int not null,
    country varchar(255) not null,
    cityId int not null,
    stateId int not null,
    foreign key (cityId) references City(cityId),
    foreign key (stateId) references State(stateId)
    
)engine=InnoDB,collate=latin1_general_cs;

create table Person (
	personId int not null primary key auto_increment,
	personCode varchar(255) not null,
    addressId int not null,
    firstName varchar(255) not null,
    lastName varchar(255) not null,
    brokerStatus varchar(255),
    secIdentity varchar(255),
    foreign key (addressId) references Address(addressId)
    
)engine=InnoDB,collate=latin1_general_cs;

create table Email (
	personId int not null,
    email varchar(255),
    foreign key (personId) references Person(personId)
    
)engine=InnoDB,collate=latin1_general_cs;

create table Portfolio (
	portfolioId int not null primary key auto_increment,
    portfolioCode varchar(255) not null,
    ownerCode varchar(255) not null,
    managerCode varchar(255) not null,
    beneficiaryCode varchar(255),
    personId int not null,
    foreign key (personId) references Person(personId)
    
)engine=InnoDB,collate=latin1_general_cs;

create table Asset (
	assetId int not null primary key auto_increment,
	assetCode varchar(255) not null,
    label varchar(255) not null,
    apr double,
    quarterlyDividend double,
    baseRateReturn double,
    betaMeasure double,
    stockSymbol varchar(10),
    sharePrice double,
    baseOmegaMeasure double,
    totalValue double
    
)engine=InnoDB,collate=latin1_general_cs;

create table AssetPortfolio (
	portfolioId int,
    foreign key (portfolioId) references Portfolio(portfolioId),
    assetValue double,
    assetId int,
    foreign key (assetId) references Asset(assetId)
    
)engine=InnoDB,collate=latin1_general_cs;

insert into City(city) values ('Huston');
insert into City(city) values ('San Diego');
insert into City(city) values ('Louisville');
insert into City(city) values ('Pensacola');
insert into City(city) values ('Miami');
insert into City(city) values ('Seattle');
insert into City(city) values ('Irvine');
insert into City(city) values ('Whittier');
insert into City(city) values ('Ventura');
insert into City(city) values ('San Antonio');
insert into City(city) values ('Beaverton');

insert into State(state) values ('TX');
insert into State(state) values ('CA');
insert into State(state) values ('KY');
insert into State(state) values ('FL');
insert into State(state) values ('WA');
insert into State(state) values ('OR');

insert into Address(street, zip, country, cityId, stateId) values ('00 Veith Center', '77015', 'US', (select c.cityId from City c where c.city = 'Huston'), (select s.stateId from State s where s.state = 'TX'));
insert into Address(street, zip, country, cityId, stateId) values ('30471 Veith Avenue', '92191', 'US', (select c.cityId from City c where c.city = 'San Diego'), (select s.stateId from State s where s.state = 'CA'));
insert into Address(street, zip, country, cityId, stateId) values ('82130 Golf Parkway', '40210', 'US', (select c.cityId from City c where c.city = 'Louisville'), (select s.stateId from State s where s.state = 'KY'));
insert into Address(street, zip, country, cityId, stateId) values ('5587 Mockingbird Place', '32595', 'US', (select c.cityId from City c where c.city = 'Pensacola'), (select s.stateId from State s where s.state = 'FL'));
insert into Address(street, zip, country, cityId, stateId) values ('2536 Delaware Alley', '33129', 'US', (select c.cityId from City c where c.city = 'Miami'), (select s.stateId from State s where s.state = 'FL'));
insert into Address(street, zip, country, cityId, stateId) values ('37941 Jackson Lane', '98166', 'US', (select c.cityId from City c where c.city = 'Seattle'), (select s.stateId from State s where s.state = 'WA'));
insert into Address(street, zip, country, cityId, stateId) values ('83791 Fairfield Hill', '92612', 'US', (select c.cityId from City c where c.city = 'Irvine'), (select s.stateId from State s where s.state = 'CA'));
insert into Address(street, zip, country, cityId, stateId) values ('35226 Susan Center', '90605', 'US', (select c.cityId from City c where c.city = 'Whittier'), (select s.stateId from State s where s.state = 'CA'));
insert into Address(street, zip, country, cityId, stateId) values ('91421 Karstens Street', '93005', 'US', (select c.cityId from City c where c.city = 'Ventura'), (select s.stateId from State s where s.state = 'CA'));
insert into Address(street, zip, country, cityId, stateId) values ('8 Summerview Park', '78255', 'US', (select c.cityId from City c where c.city = 'San Antonio'), (select s.stateId from State s where s.state = 'TX'));
insert into Address(street, zip, country, cityId, stateId) values ('10 Acker Court', '97075', 'US', (select c.cityId from City c where c.city = 'Beaverton'), (select s.stateId from State s where s.state = 'OR'));

insert into Person(personCode, addressId, firstName, lastName, brokerStatus, secIdentity) values ('1AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '00 Veith Center'), 'Elia', 'Stickney', 'J', 'sec001');
insert into Person(personCode, addressId, firstName, lastName) values ('2AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '30471 Veith Avenue'), 'Paulita', 'Priddy');
insert into Person(personCode, addressId, firstName, lastName, brokerStatus, secIdentity) values ('3AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '82130 Golf Parkway'), 'Mellisa', 'Upston', 'E', 'sec002');
insert into Person(personCode, addressId, firstName, lastName) values ('4AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '5587 Mockingbird Place'), 'Jefferson', 'Melwall');
insert into Person(personCode, addressId, firstName, lastName) values ('5AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '2536 Delaware Alley'), 'Emmanuel', 'Culley');
insert into Person(personCode, addressId, firstName, lastName, brokerStatus, secIdentity) values ('6AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '37941 Jackson Lane'), 'Lorenza', 'Lowndsborough', 'E', 'sec003');
insert into Person(personCode, addressId, firstName, lastName) values ('7AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '83791 Fairfield Hill'), 'Meech', 'Vincents');
insert into Person(personCode, addressId, firstName, lastName) values ('8AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '35226 Susan Center'), 'Ursa', 'Andrus');
insert into Person(personCode, addressId, firstName, lastName) values ('9AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '91421 Karstens Street'), 'Madonna', 'Gatherell');
insert into Person(personCode, addressId, firstName, lastName, brokerStatus, secIdentity) values ('10AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '8 Summerview Park'), 'Adolpho', 'Brabin', 'J', 'sec004');
insert into Person(personCode, addressId, firstName, lastName) values ('11AB', (Select a.addressId from Address a left join City c on a.cityId = c.cityId where a.street = '10 Acker Court'), 'Randolph', 'Uccello');

insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '1AB'), 'hsalazar0@apache.org');
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '2AB'), 'asparey1@examiner.com');
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '5AB'), 'ahitcham4@japanpost.jp');
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '5AB'), 'ahitc@gmail.com');
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '7AB'), 'geberlein6@tripod.com');
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '8AB'), 'lmacneill7@marketwatch.com');
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '10AB'), 'mbunstone8@wunderground.com');
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '11AB'), 'kdrewclifton9@msn.com');
insert into Email(personId, email) values ((select p.personId from Person p where p.personCode = '11AB'), 'kdrewc9@gmail.com');

insert into Portfolio(portfolioCode, ownerCode, managerCode, beneficiaryCode, personId) values ('1CB', '2AB', '1AB', '2AB', (select personId from Person p where p.personCode = '2AB'));
insert into Portfolio(portfolioCode, ownerCode, managerCode, personId) values ('2CB', '4AB', '1AB', (select personId from Person p where p.personCode = '4AB'));
insert into Portfolio(portfolioCode, ownerCode, managerCode, personId) values ('3CB', '5AB', '1AB', (select personId from Person p where p.personCode = '5AB'));
insert into Portfolio(portfolioCode, ownerCode, managerCode, personId) values ('4CB', '7AB', '3AB', (select personId from Person p where p.personCode = '7AB'));
insert into Portfolio(portfolioCode, ownerCode, managerCode, beneficiaryCode, personId) values ('5CB', '8AB', '3AB', '9AB', (select personId from Person p where p.personCode = '8AB'));
insert into Portfolio(portfolioCode, ownerCode, managerCode, personId) values ('6CB', '9AB', '3AB', (select personId from Person p where p.personCode = '9AB'));
insert into Portfolio(portfolioCode, ownerCode, managerCode, personId) values ('7CB', '11AB', '6AB', (select personId from Person p where p.personCode = '11AB'));
insert into Portfolio(portfolioCode, ownerCode, managerCode, beneficiaryCode, personId) values ('8CB', '2AB', '6AB', '1AB', (select personId from Person p where p.personCode = '2AB'));
insert into Portfolio(portfolioCode, ownerCode, managerCode, beneficiaryCode, personId) values ('9CB', '1AB', '10AB', '2AB', (select personId from Person p where p.personCode = '1AB'));

insert into Asset(assetCode, label, apr) values ('1BB', '1-year CD', 2.4);
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, betaMeasure, stockSymbol, sharePrice) values('2BB', 'Fitbit', 0.00, 7.2, -0.21, 'FIT', 6.30);
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, baseOmegaMeasure, totalValue) values('3BB', 'Rental Property 1', 10300, 12.5, 0.4, 130500);
insert into Asset(assetCode, label, apr) values ('4BB', 'IRA', 3.4);
insert into Asset(assetCode, label, apr) values ('5BB', '5-year CD', 5.4);
insert into Asset(assetCode, label, apr) values ('6BB', 'Money Market Savings', 4.3);
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, betaMeasure, stockSymbol, sharePrice) values('7BB', 'Plug Power', 0.00, 4.2, 0.44, 'PLUG', 4.25);
insert into Asset(assetCode, label, apr) values ('8BB', '10-year CD', 10.4);
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, betaMeasure, stockSymbol, sharePrice) values('9BB', 'Altice USA', 3.24, 0.23, 0.08, 'ATUS', 25.81);
insert into Asset(assetCode, label, apr) values ('10BB', 'Savings Account', 5.5);
insert into Asset(assetCode, label, apr) values ('11BB', 'Savings Account', 3.9);
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, baseOmegaMeasure, totalValue) values('12BB', 'Rental Property 2', 15000, 10.5, 0.4, 1300000);
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, baseOmegaMeasure, totalValue) values('13BB', 'Rental Property 3', 20300, 40.5, 0.4, 155000);
insert into Asset(assetCode, label, apr) values ('14BB', '5-year CD', 4.35);
insert into Asset(assetCode, label, quarterlyDividend, baseRateReturn, betaMeasure, stockSymbol, sharePrice) values('15BB', 'Enterprise Products', 0.445, 0.89, -0.03, 'EPD', 22.68);
insert into Asset(assetCode, label, apr) values ('16BB', '1-year CD', 3.45);

insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '1CB' and p.ownerCode = '2AB'), 100, (select a.assetId from Asset a where a.assetCode = '1BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '1CB' and p.ownerCode = '2AB'), 424.5, (select a.assetId from Asset a where a.assetCode = '16BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '1CB' and p.ownerCode = '2AB'), 4.68, (select a.assetId from Asset a where a.assetCode = '2BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '2CB' and p.ownerCode = '4AB'), 12.4, (select a.assetId from Asset a where a.assetCode = '3BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '2CB' and p.ownerCode = '4AB'), 40, (select a.assetId from Asset a where a.assetCode = '4BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '3CB' and p.ownerCode = '5AB'), 2300, (select a.assetId from Asset a where a.assetCode = '5BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '3CB' and p.ownerCode = '5AB'), 20, (select a.assetId from Asset a where a.assetCode = '7BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '4CB' and p.ownerCode = '7AB'), 1230, (select a.assetId from Asset a where a.assetCode = '6BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '5CB' and p.ownerCode = '8AB'), 42.4, (select a.assetId from Asset a where a.assetCode = '12BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '5CB' and p.ownerCode = '8AB'), 190.34, (select a.assetId from Asset a where a.assetCode = '9BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '6CB' and p.ownerCode = '9AB'), 5.43, (select a.assetId from Asset a where a.assetCode = '13BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '6CB' and p.ownerCode = '9AB'), 124.21, (select a.assetId from Asset a where a.assetCode = '8BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '7CB' and p.ownerCode = '11AB'), 7, (select a.assetId from Asset a where a.assetCode = '15BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '7CB' and p.ownerCode = '11AB'), 5049.4, (select a.assetId from Asset a where a.assetCode = '10BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '8CB' and p.ownerCode = '2AB'), 43253.3, (select a.assetId from Asset a where a.assetCode = '11BB'));
insert into AssetPortfolio(portfolioId, assetValue, assetId) values ((select p.portfolioId from Portfolio p where p.portfolioCode = '9CB' and p.ownerCode = '1AB'), 8493.3, (select a.assetId from Asset a where a.assetCode = '14BB'));

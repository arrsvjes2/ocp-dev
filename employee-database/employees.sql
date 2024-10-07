--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;



SET default_tablespace = '';

SET default_with_oids = false;


---
--- drop tables
---
DROP TABLE IF EXISTS employee_territories;
DROP TABLE IF EXISTS territories;
DROP TABLE IF EXISTS region;
DROP TABLE IF EXISTS employees;


--
-- Name: employees; Type: TABLE; Schema: public; Owner: -; Tablespace:
--

CREATE TABLE employees (
    employee_id smallint NOT NULL,
    last_name character varying(20) NOT NULL,
    first_name character varying(10) NOT NULL,
    title character varying(30),
    title_of_courtesy character varying(25),
    birth_date date,
    hire_date date,
    address character varying(60),
    city character varying(15),
    region character varying(15),
    postal_code character varying(10),
    country character varying(15),
    home_phone character varying(24),
    extension character varying(4),
    photo bytea,
    notes text,
    reports_to smallint,
    photo_path character varying(255)
);

--
-- Name: employee_territories; Type: TABLE; Schema: public; Owner: -; Tablespace:
--

CREATE TABLE employee_territories (
    employee_id smallint NOT NULL,
    territory_id character varying(20) NOT NULL
);

--
-- Name: region; Type: TABLE; Schema: public; Owner: -; Tablespace:
--

CREATE TABLE region (
    region_id smallint NOT NULL,
    region_description character varying(60) NOT NULL
);

--
-- Name: territories; Type: TABLE; Schema: public; Owner: -; Tablespace:
--

CREATE TABLE territories (
    territory_id character varying(20) NOT NULL,
    territory_description character varying(60) NOT NULL,
    region_id smallint NOT NULL
);



--
-- Data for Name: region; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO region (region_id, region_description) VALUES (1, 'Eastern');
INSERT INTO region (region_id, region_description) VALUES (2, 'Western');
INSERT INTO region (region_id, region_description) VALUES (3, 'Northern');
INSERT INTO region (region_id, region_description) VALUES (4, 'Southern');


--
-- Data for Name: territories (territory_id, territory_description,region_id); Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('01581', 'Westboro', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('01730', 'Bedford', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('01833', 'Georgetow', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('02116', 'Boston', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('02139', 'Cambridge', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('02184', 'Braintree', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('02903', 'Providence', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('03049', 'Hollis', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('03801', 'Portsmouth', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('06897', 'Wilton', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('07960', 'Morristown', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('08837', 'Edison', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('10019', 'New York', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('10038', 'New York', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('11747', 'Mellvile', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('14450', 'Fairport', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('19428', 'Philadelphia', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('19713', 'Neward', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('20852', 'Rockville', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('27403', 'Greensboro', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('27511', 'Cary', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('29202', 'Columbia', 4);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('30346', 'Atlanta', 4);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('31406', 'Savannah', 4);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('32859', 'Orlando', 4);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('33607', 'Tampa', 4);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('40222', 'Louisville', 1);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('44122', 'Beachwood', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('45839', 'Findlay', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('48075', 'Southfield', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('48084', 'Troy', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('48304', 'Bloomfield Hills', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('53404', 'Racine', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('55113', 'Roseville', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('55439', 'Minneapolis', 3);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('60179', 'Hoffman Estates', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('60601', 'Chicago', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('72716', 'Bentonville', 4);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('75234', 'Dallas', 4);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('78759', 'Austin', 4);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('80202', 'Denver', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('80909', 'Colorado Springs', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('85014', 'Phoenix', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('85251', 'Scottsdale', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('90405', 'Santa Monica', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('94025', 'Menlo Park', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('94105', 'San Francisco', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('95008', 'Campbell', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('95054', 'Santa Clara', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('95060', 'Santa Cruz', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('98004', 'Bellevue', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('98052', 'Redmond', 2);
INSERT INTO territories (territory_id, territory_description,region_id) VALUES ('98104', 'Seattle', 2);


--
-- Data for Name: employees; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO employees (employee_id, last_name, first_name, title, title_of_courtesy, birth_date, hire_date, address, city, region, postal_code, country, home_phone, extension, photo, notes, reports_to, photo_path) VALUES (2, 'Fuller', 'Andrew', 'Vice President, Sales', 'Dr.', '1952-02-19', '1992-08-14', '908 W. Capital Way', 'Tacoma', 'WA', '98401', 'USA', '(206) 555-9482', '3457', '\x', 'Andrew received his BTS commercial in 1974 and a Ph.D. in international marketing from the University of Dallas in 1981.  He is fluent in French and Italian and reads German.  He joined the company as a sales representative, was promoted to sales manager in January 1992 and to vice president of sales in March 1993.  Andrew is a member of the Sales Management Roundtable, the Seattle Chamber of Commerce, and the Pacific Rim Importers Association.', NULL, 'http://accweb/emmployees/fuller.bmp');
INSERT INTO employees (employee_id, last_name, first_name, title, title_of_courtesy, birth_date, hire_date, address, city, region, postal_code, country, home_phone, extension, photo, notes, reports_to, photo_path) VALUES (1, 'Davolio', 'Nancy', 'Sales Representative', 'Ms.', '1948-12-08', '1992-05-01', '507 - 20th Ave. E.\nApt. 2A', 'Seattle', 'WA', '98122', 'USA', '(206) 555-9857', '5467', '\x', 'Education includes a BA in psychology from Colorado State University in 1970.  She also completed The Art of the Cold Call.  Nancy is a member of Toastmasters International.', 2, 'http://accweb/emmployees/davolio.bmp');
INSERT INTO employees (employee_id, last_name, first_name, title, title_of_courtesy, birth_date, hire_date, address, city, region, postal_code, country, home_phone, extension, photo, notes, reports_to, photo_path) VALUES (3, 'Leverling', 'Janet', 'Sales Representative', 'Ms.', '1963-08-30', '1992-04-01', '722 Moss Bay Blvd.', 'Kirkland', 'WA', '98033', 'USA', '(206) 555-3412', '3355', '\x', 'Janet has a BS degree in chemistry from Boston College (1984).  She has also completed a certificate program in food retailing management.  Janet was hired as a sales associate in 1991 and promoted to sales representative in February 1992.', 2, 'http://accweb/emmployees/leverling.bmp');
INSERT INTO employees (employee_id, last_name, first_name, title, title_of_courtesy, birth_date, hire_date, address, city, region, postal_code, country, home_phone, extension, photo, notes, reports_to, photo_path) VALUES (4, 'Peacock', 'Margaret', 'Sales Representative', 'Mrs.', '1937-09-19', '1993-05-03', '4110 Old Redmond Rd.', 'Redmond', 'WA', '98052', 'USA', '(206) 555-8122', '5176', '\x', 'Margaret holds a BA in English literature from Concordia College (1958) and an MA from the American Institute of Culinary Arts (1966).  She was assigned to the London office temporarily from July through November 1992.', 2, 'http://accweb/emmployees/peacock.bmp');
INSERT INTO employees (employee_id, last_name, first_name, title, title_of_courtesy, birth_date, hire_date, address, city, region, postal_code, country, home_phone, extension, photo, notes, reports_to, photo_path) VALUES (5, 'Buchanan', 'Steven', 'Sales Manager', 'Mr.', '1955-03-04', '1993-10-17', '14 Garrett Hill', 'London', NULL, 'SW1 8JR', 'UK', '(71) 555-4848', '3453', '\x', 'Steven Buchanan graduated from St. Andrews University, Scotland, with a BSC degree in 1976.  Upon joining the company as a sales representative in 1992, he spent 6 months in an orientation program at the Seattle office and then returned to his permanent post in London.  He was promoted to sales manager in March 1993.  Mr. Buchanan has completed the courses Successful Telemarketing and International Sales Management.  He is fluent in French.', 2, 'http://accweb/emmployees/buchanan.bmp');
INSERT INTO employees (employee_id, last_name, first_name, title, title_of_courtesy, birth_date, hire_date, address, city, region, postal_code, country, home_phone, extension, photo, notes, reports_to, photo_path) VALUES (6, 'Suyama', 'Michael', 'Sales Representative', 'Mr.', '1963-07-02', '1993-10-17', 'Coventry House\nMiner Rd.', 'London', NULL, 'EC2 7JR', 'UK', '(71) 555-7773', '428', '\x', 'Michael is a graduate of Sussex University (MA, economics, 1983) and the University of California at Los Angeles (MBA, marketing, 1986).  He has also taken the courses Multi-Cultural Selling and Time Management for the Sales Professional.  He is fluent in Japanese and can read and write French, Portuguese, and Spanish.', 5, 'http://accweb/emmployees/davolio.bmp');
INSERT INTO employees (employee_id, last_name, first_name, title, title_of_courtesy, birth_date, hire_date, address, city, region, postal_code, country, home_phone, extension, photo, notes, reports_to, photo_path) VALUES (7, 'King', 'Robert', 'Sales Representative', 'Mr.', '1960-05-29', '1994-01-02', 'Edgeham Hollow\nWinchester Way', 'London', NULL, 'RG1 9SP', 'UK', '(71) 555-5598', '465', '\x', 'Robert King served in the Peace Corps and traveled extensively before completing his degree in English at the University of Michigan in 1992, the year he joined the company.  After completing a course entitled Selling in Europe, he was transferred to the London office in March 1993.', 5, 'http://accweb/emmployees/davolio.bmp');
INSERT INTO employees (employee_id, last_name, first_name, title, title_of_courtesy, birth_date, hire_date, address, city, region, postal_code, country, home_phone, extension, photo, notes, reports_to, photo_path) VALUES (8, 'Callahan', 'Laura', 'Inside Sales Coordinator', 'Ms.', '1958-01-09', '1994-03-05', '4726 - 11th Ave. N.E.', 'Seattle', 'WA', '98105', 'USA', '(206) 555-1189', '2344', '\x', 'Laura received a BA in psychology from the University of Washington.  She has also completed a course in business French.  She reads and writes French.', 2, 'http://accweb/emmployees/davolio.bmp');
INSERT INTO employees (employee_id, last_name, first_name, title, title_of_courtesy, birth_date, hire_date, address, city, region, postal_code, country, home_phone, extension, photo, notes, reports_to, photo_path) VALUES (9, 'Dodsworth', 'Anne', 'Sales Representative', 'Ms.', '1966-01-27', '1994-11-15', '7 Houndstooth Rd.', 'London', NULL, 'WG2 7LT', 'UK', '(71) 555-4444', '452', '\x', 'Anne has a BA degree in English from St. Lawrence College.  She is fluent in French and German.', 5, 'http://accweb/emmployees/davolio.bmp');

--
-- Name: pk_employees; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace:
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT pk_employees PRIMARY KEY (employee_id);

--
-- Name: pk_employee_territories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace:
--

ALTER TABLE ONLY employee_territories
    ADD CONSTRAINT pk_employee_territories PRIMARY KEY (employee_id, territory_id);

--
-- Name: pk_region; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace:
--

ALTER TABLE ONLY region
    ADD CONSTRAINT pk_region PRIMARY KEY (region_id);

--
-- Name: pk_territories; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace:
--

ALTER TABLE ONLY territories
    ADD CONSTRAINT pk_territories PRIMARY KEY (territory_id);

--
-- Name: fk_territories_region; Type: Constraint; Schema: -; Owner: -
--

ALTER TABLE ONLY territories
    ADD CONSTRAINT fk_territories_region FOREIGN KEY (region_id) REFERENCES region;


--
-- Name: fk_employee_territories_territories; Type: Constraint; Schema: -; Owner: -
--

ALTER TABLE ONLY employee_territories
    ADD CONSTRAINT fk_employee_territories_territories FOREIGN KEY (territory_id) REFERENCES territories;

--
-- Name: fk_employee_territories_employees; Type: Constraint; Schema: -; Owner: -
--

ALTER TABLE ONLY employee_territories
    ADD CONSTRAINT fk_employee_territories_employees FOREIGN KEY (employee_id) REFERENCES employees;

--
-- Name: fk_employees_employees; Type: Constraint; Schema: -; Owner: -
--

ALTER TABLE ONLY employees
    ADD CONSTRAINT fk_employees_employees FOREIGN KEY (reports_to) REFERENCES employees;
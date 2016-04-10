-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 10, 2016 at 05:08 AM
-- Server version: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `personaltutor`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `getnearesttutor`(IN `lng` VARCHAR(25) CHARSET swe7, IN `lat` VARCHAR(25) CHARSET swe7)
    READS SQL DATA
    COMMENT 'to get the nearest tutor based on location'
SELECT addressid, ( 3959 * acos( cos( radians(lat) ) * cos( radians( lattitude ) ) * 
cos( radians( longitude ) - radians(lng) ) + sin( radians(lat) ) * 
sin( radians( lattitude ) ) ) ) AS distance FROM address  ORDER BY distance LIMIT 0 , 20$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `address`
--

CREATE TABLE IF NOT EXISTS `address` (
`AddressId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `AddressLine1` varchar(50) NOT NULL,
  `AddressLine2` varchar(50) NOT NULL,
  `City` varchar(50) NOT NULL,
  `State` varchar(2) NOT NULL,
  `Zipcode` varchar(20) NOT NULL,
  `Lattitude` varchar(25) NOT NULL,
  `Longitude` varchar(25) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`AddressId`, `UserId`, `AddressLine1`, `AddressLine2`, `City`, `State`, `Zipcode`, `Lattitude`, `Longitude`) VALUES
(16, 20, '513 summit Ave', 'Apt 178', 'Arlington', 'TX', '76013', '32.731616', ' -97.121449'),
(19, 23, '814 Arkansas Dr.', '', 'Arlington', 'TX', '76013', '32.706107', '-97.1172421'),
(22, 26, '701 s Nedderman dr', '', 'Arlington', 'TX', '76019', '32.729535', '-97.114895'),
(23, 7, '400 W 7th Ave', '', 'Stillwater', 'OK', '74074', '36.115238', '-97.063211');

-- --------------------------------------------------------

--
-- Table structure for table `availability`
--

CREATE TABLE IF NOT EXISTS `availability` (
`AvailabilityId` int(11) NOT NULL,
  `ServiceId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `Day` varchar(10) NOT NULL,
  `StartTime` varchar(10) NOT NULL,
  `EndTime` varchar(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `availability`
--

INSERT INTO `availability` (`AvailabilityId`, `ServiceId`, `UserId`, `Day`, `StartTime`, `EndTime`) VALUES
(4, 9, 20, 'Mon', '08:00 PM', '11:00 PM'),
(5, 9, 20, 'Tue', '08:00 PM', '11:00 PM'),
(6, 9, 20, 'Wed', '08:00 PM', '11:00 PM'),
(7, 10, 26, 'Mon', '07:00 PM', '10:00 PM'),
(8, 10, 26, 'Tue', '07:00 PM', '10:00 PM'),
(9, 10, 26, 'Wed', '07:00 PM', '10:00 PM'),
(10, 10, 26, 'Thu', '07:00 PM', '10:00 PM'),
(11, 10, 26, 'Fri', '07:00 PM', '10:00 PM'),
(12, 11, 23, 'Mon', '07:00 PM', '10:00 PM'),
(13, 11, 23, 'Tue', '07:00 PM', '10:00 PM'),
(14, 11, 23, 'Wed', '07:00 PM', '10:00 PM'),
(15, 11, 23, 'Thu', '07:00 PM', '10:00 PM'),
(16, 11, 23, 'Fri', '07:00 PM', '10:00 PM'),
(17, 12, 7, 'Mon', '06:00 PM', '10:00 PM'),
(18, 12, 7, 'Tue', '06:00 PM', '10:00 PM'),
(19, 12, 7, 'Wed', '06:00 PM', '10:00 PM'),
(20, 12, 7, 'Thu', '06:00 PM', '10:00 PM'),
(21, 12, 7, 'Fri', '06:00 PM', '10:00 PM'),
(22, -1, 20, 'Mon', '07:55 AM', '09:55 AM'),
(23, -1, 20, 'Tue', '07:55 AM', '09:55 AM'),
(24, -1, 20, 'Sun', '07:55 AM', '09:55 AM'),
(25, 13, 20, 'Tue', '06:45 AM', '07:45 AM'),
(26, 13, 20, 'Wed', '06:45 AM', '07:45 AM'),
(27, 14, 20, 'Mon', '10:29 PM', '11:29 PM'),
(28, 14, 20, 'Tue', '10:29 PM', '11:29 PM'),
(29, 14, 20, 'Wed', '10:29 PM', '11:29 PM'),
(30, 14, 20, 'Thu', '10:29 PM', '11:29 PM'),
(31, 14, 20, 'Fri', '10:29 PM', '11:29 PM'),
(32, -1, 20, 'Mon', '07:00 AM', '09:00 AM'),
(33, -1, 20, 'Fri', '07:00 AM', '09:00 AM'),
(34, -1, 20, 'Sat', '07:00 AM', '09:00 AM'),
(35, -1, 20, 'Sun', '07:00 AM', '09:00 AM'),
(36, -1, 20, 'Tue', '09:00 AM', '11:00 AM'),
(37, -1, 20, 'Wed', '09:00 AM', '11:00 AM'),
(38, 15, 20, 'Mon', '11:13 PM', '12:13 AM'),
(39, 15, 20, 'Tue', '11:13 PM', '12:13 AM');

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE IF NOT EXISTS `category` (
`CategoryId` int(11) NOT NULL,
  `CategoryName` varchar(50) NOT NULL,
  `isCategoryActive` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`CategoryId`, `CategoryName`, `isCategoryActive`) VALUES
(1, 'Science', 1),
(2, 'Music', 1),
(3, 'Programming Language', 1),
(4, 'Mobile Apps', 1),
(5, 'Arts', 1);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
`UserId` int(11) NOT NULL,
  `UserTypeId` int(11) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`UserId`, `UserTypeId`, `Email`, `Password`) VALUES
(7, 1, 'zhenyu.chen@mavs.uta.edu', '81dc9bdb52d04dc20036dbd8313ed055'),
(20, 1, 'arun.gopinathan@mavs.uta.edu', '81dc9bdb52d04dc20036dbd8313ed055'),
(21, 1, 'jhf@hgf.com', '202cb962ac59075b964b07152d234b70'),
(23, 1, 'mousax@gmail.com', '81dc9bdb52d04dc20036dbd8313ed055'),
(26, 1, 'funda@uta.edu', '81dc9bdb52d04dc20036dbd8313ed055');

-- --------------------------------------------------------

--
-- Table structure for table `personalinfo`
--

CREATE TABLE IF NOT EXISTS `personalinfo` (
`PersonalInfoId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `FirstName` varchar(30) NOT NULL,
  `LastName` varchar(30) NOT NULL,
  `PhoneNumber` varchar(15) NOT NULL,
  `AddressId` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `personalinfo`
--

INSERT INTO `personalinfo` (`PersonalInfoId`, `UserId`, `FirstName`, `LastName`, `PhoneNumber`, `AddressId`) VALUES
(8, 7, 'Zhenyu', 'Chen', '682-234-0909', 23),
(22, 20, 'Arun', 'Gopinathan', '6822340909', 16),
(25, 23, 'Mousa', 'Almotairi', '2144044845', 19),
(28, 26, 'Funda', 'Karapinar-Reilly', '6822031343', 22);

-- --------------------------------------------------------

--
-- Table structure for table `review`
--

CREATE TABLE IF NOT EXISTS `review` (
`ReviewId` int(11) NOT NULL,
  `ServiceId` int(11) NOT NULL,
  `Rating` double NOT NULL,
  `Title` varchar(50) NOT NULL,
  `Comment` varchar(200) NOT NULL,
  `RaterUserId` int(11) DEFAULT NULL,
  `HelpfulCount` int(11) NOT NULL DEFAULT '0',
  `UnHelpfulCount` int(11) NOT NULL DEFAULT '0',
  `isAnonymous` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `review`
--

INSERT INTO `review` (`ReviewId`, `ServiceId`, `Rating`, `Title`, `Comment`, `RaterUserId`, `HelpfulCount`, `UnHelpfulCount`, `isAnonymous`) VALUES
(1, 2, 3.5, 'Awesome !!! ', 'It is a great course!. ', 4, 1, 1, 0),
(2, 2, 3.5, 'Awesome !!! ', 'It is a great course!. ', NULL, 0, 0, 1);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

CREATE TABLE IF NOT EXISTS `service` (
`ServiceId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `CategoryId` int(11) NOT NULL,
  `SubCategoryId` int(11) NOT NULL,
  `PricePerHour` int(11) NOT NULL,
  `DistanceWillingToTravelInMiles` int(11) NOT NULL,
  `AvgRating` double DEFAULT NULL,
  `NumOfFeedback` int(11) DEFAULT NULL,
  `isAdvertised` varchar(3) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`ServiceId`, `UserId`, `CategoryId`, `SubCategoryId`, `PricePerHour`, `DistanceWillingToTravelInMiles`, `AvgRating`, `NumOfFeedback`, `isAdvertised`) VALUES
(9, 20, 4, 7, 46, 20, 4.5, 0, 'NO'),
(10, 26, 4, 8, 25, 20, NULL, 0, 'NO'),
(11, 23, 2, 4, 100, 20, NULL, 0, 'NO'),
(12, 7, 3, 9, 50, 25, NULL, 0, 'NO'),
(13, 20, 3, 9, 50, 20, NULL, NULL, 'YES'),
(14, 20, 4, 8, 65, 20, NULL, NULL, 'YES'),
(15, 20, 2, 5, 100, 20, NULL, NULL, 'NO');

-- --------------------------------------------------------

--
-- Table structure for table `state_lkp`
--

CREATE TABLE IF NOT EXISTS `state_lkp` (
`StateId` int(11) NOT NULL,
  `StateName` varchar(2) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `state_lkp`
--

INSERT INTO `state_lkp` (`StateId`, `StateName`) VALUES
(1, 'AL'),
(2, 'AK'),
(3, 'AZ'),
(4, 'AR'),
(5, 'CA'),
(6, 'CO'),
(7, 'CT'),
(8, 'DE'),
(9, 'FL'),
(10, 'GA'),
(11, 'HI'),
(12, 'ID'),
(13, 'IL'),
(14, 'IN'),
(15, 'IA'),
(16, 'KS'),
(17, 'KY'),
(18, 'LA'),
(19, 'ME'),
(20, 'MD'),
(21, 'MA'),
(22, 'MI'),
(23, 'MN'),
(24, 'MS'),
(25, 'MO'),
(26, 'MT'),
(27, 'NE'),
(28, 'NV'),
(29, 'NH'),
(30, 'NJ'),
(31, 'NM'),
(32, 'NY'),
(33, 'NC'),
(34, 'ND'),
(35, 'OH'),
(36, 'OK'),
(37, 'OR'),
(38, 'PA'),
(39, 'RI'),
(40, 'SC'),
(41, 'SD'),
(42, 'TN'),
(43, 'TX'),
(44, 'UT'),
(45, 'VT'),
(46, 'VA'),
(47, 'WA'),
(48, 'WV'),
(49, 'WI'),
(50, 'WY');

-- --------------------------------------------------------

--
-- Table structure for table `subcategory`
--

CREATE TABLE IF NOT EXISTS `subcategory` (
`SubCategoryId` int(11) NOT NULL,
  `CategoryId` int(11) NOT NULL,
  `SubCategoryName` varchar(50) NOT NULL,
  `isSubCategoryActive` int(11) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `subcategory`
--

INSERT INTO `subcategory` (`SubCategoryId`, `CategoryId`, `SubCategoryName`, `isSubCategoryActive`) VALUES
(3, 1, 'Physics', 1),
(4, 2, 'Piano', 1),
(5, 2, 'Guitar', 1),
(6, 2, 'Violin', 1),
(7, 4, 'Android', 1),
(8, 4, 'iOS', 1),
(9, 3, 'Java', 1),
(10, 3, 'Python', 1),
(11, 5, 'Painting', 1);

-- --------------------------------------------------------

--
-- Table structure for table `usertype`
--

CREATE TABLE IF NOT EXISTS `usertype` (
`UserTypeId` int(11) NOT NULL,
  `Description` varchar(10) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usertype`
--

INSERT INTO `usertype` (`UserTypeId`, `Description`) VALUES
(1, 'Tutor'),
(2, 'Student');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `address`
--
ALTER TABLE `address`
 ADD PRIMARY KEY (`AddressId`);

--
-- Indexes for table `availability`
--
ALTER TABLE `availability`
 ADD PRIMARY KEY (`AvailabilityId`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
 ADD PRIMARY KEY (`CategoryId`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
 ADD PRIMARY KEY (`UserId`);

--
-- Indexes for table `personalinfo`
--
ALTER TABLE `personalinfo`
 ADD PRIMARY KEY (`PersonalInfoId`);

--
-- Indexes for table `review`
--
ALTER TABLE `review`
 ADD PRIMARY KEY (`ReviewId`);

--
-- Indexes for table `service`
--
ALTER TABLE `service`
 ADD PRIMARY KEY (`ServiceId`);

--
-- Indexes for table `state_lkp`
--
ALTER TABLE `state_lkp`
 ADD PRIMARY KEY (`StateId`);

--
-- Indexes for table `subcategory`
--
ALTER TABLE `subcategory`
 ADD PRIMARY KEY (`SubCategoryId`);

--
-- Indexes for table `usertype`
--
ALTER TABLE `usertype`
 ADD PRIMARY KEY (`UserTypeId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `address`
--
ALTER TABLE `address`
MODIFY `AddressId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `availability`
--
ALTER TABLE `availability`
MODIFY `AvailabilityId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=40;
--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
MODIFY `CategoryId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
MODIFY `UserId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=27;
--
-- AUTO_INCREMENT for table `personalinfo`
--
ALTER TABLE `personalinfo`
MODIFY `PersonalInfoId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=29;
--
-- AUTO_INCREMENT for table `review`
--
ALTER TABLE `review`
MODIFY `ReviewId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
MODIFY `ServiceId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT for table `state_lkp`
--
ALTER TABLE `state_lkp`
MODIFY `StateId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=51;
--
-- AUTO_INCREMENT for table `subcategory`
--
ALTER TABLE `subcategory`
MODIFY `SubCategoryId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `usertype`
--
ALTER TABLE `usertype`
MODIFY `UserTypeId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

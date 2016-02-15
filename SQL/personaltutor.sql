-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 15, 2016 at 08:45 PM
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
  `Lattitude` varchar(15) NOT NULL,
  `Longitude` varchar(15) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `address`
--

INSERT INTO `address` (`AddressId`, `UserId`, `AddressLine1`, `AddressLine2`, `City`, `State`, `Zipcode`, `Lattitude`, `Longitude`) VALUES
(4, 6, '513 Summit Ave', 'Apt 178', 'Arlington', 'TX', '76013', 'ABC', 'DEF');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
`UserId` int(11) NOT NULL,
  `UserTypeId` int(11) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(200) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`UserId`, `UserTypeId`, `Email`, `Password`) VALUES
(6, 1, 'arun.gopinathan@mavs.uta.edu', '1234');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `personalinfo`
--

INSERT INTO `personalinfo` (`PersonalInfoId`, `UserId`, `FirstName`, `LastName`, `PhoneNumber`, `AddressId`) VALUES
(6, 6, 'Arun', 'Gopinathan', '682-234-0909', 4);

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
-- Indexes for table `state_lkp`
--
ALTER TABLE `state_lkp`
 ADD PRIMARY KEY (`StateId`);

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
MODIFY `AddressId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
MODIFY `UserId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `personalinfo`
--
ALTER TABLE `personalinfo`
MODIFY `PersonalInfoId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `state_lkp`
--
ALTER TABLE `state_lkp`
MODIFY `StateId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=51;
--
-- AUTO_INCREMENT for table `usertype`
--
ALTER TABLE `usertype`
MODIFY `UserTypeId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

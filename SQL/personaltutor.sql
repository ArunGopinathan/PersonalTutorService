-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 13, 2016 at 01:27 AM
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
  `State` int(11) NOT NULL,
  `Zipcode` varchar(20) NOT NULL,
  `Lattitude` varchar(15) NOT NULL,
  `Longitude` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
`UserId` int(11) NOT NULL,
  `Email` varchar(100) NOT NULL,
  `Password` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `personainfo`
--

CREATE TABLE IF NOT EXISTS `personainfo` (
`PersonalInfoId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `FirstName` varchar(30) NOT NULL,
  `LastName` varchar(30) NOT NULL,
  `PhoneNumber` varchar(15) NOT NULL,
  `AddressId` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `state_lkp`
--

CREATE TABLE IF NOT EXISTS `state_lkp` (
`StateId` int(11) NOT NULL,
  `StateName` varchar(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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
-- Indexes for table `personainfo`
--
ALTER TABLE `personainfo`
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
MODIFY `AddressId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `login`
--
ALTER TABLE `login`
MODIFY `UserId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `personainfo`
--
ALTER TABLE `personainfo`
MODIFY `PersonalInfoId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `state_lkp`
--
ALTER TABLE `state_lkp`
MODIFY `StateId` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `usertype`
--
ALTER TABLE `usertype`
MODIFY `UserTypeId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

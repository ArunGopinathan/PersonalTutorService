-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 09, 2016 at 04:43 AM
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
-- Table structure for table `service`
--

CREATE TABLE IF NOT EXISTS `service` (
`ServiceId` int(11) NOT NULL,
  `UserId` int(11) NOT NULL,
  `CategoryId` int(11) NOT NULL,
  `SubCategoryId` int(11) NOT NULL,
  `PricePerHour` int(11) NOT NULL,
  `DistanceWillingToTravelInMiles` int(11) NOT NULL,
  `AvgRating` int(11) NOT NULL,
  `isAdvertised` varchar(3) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`ServiceId`, `UserId`, `CategoryId`, `SubCategoryId`, `PricePerHour`, `DistanceWillingToTravelInMiles`, `AvgRating`, `isAdvertised`) VALUES
(3, 20, 1, 3, 40, 5, 0, 'NO'),
(4, 20, 2, 4, 60, 20, 0, 'NO'),
(5, 20, 2, 6, 30, 10, 0, 'NO'),
(6, 20, 2, 4, 17, 20, 0, 'NO'),
(7, 20, 1, 3, 20, 20, 0, 'NO'),
(8, 20, 5, 11, 20, 20, 0, 'NO');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `service`
--
ALTER TABLE `service`
 ADD PRIMARY KEY (`ServiceId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `service`
--
ALTER TABLE `service`
MODIFY `ServiceId` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=9;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

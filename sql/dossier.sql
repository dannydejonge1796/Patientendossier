-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 21 mrt 2023 om 12:56
-- Serverversie: 10.4.27-MariaDB
-- PHP-versie: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dossier`
--

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `care`
--

CREATE TABLE `care` (
  `careNumber` int(10) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `profession` varchar(255) NOT NULL,
  `phonenumber` int(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `care`
--

INSERT INTO `care` (`careNumber`, `firstname`, `lastname`, `profession`, `phonenumber`, `email`, `password`) VALUES
(987654321, 'Danny', 'Care', 'dokter', 612345678, 'danny@gmail.com', '123');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `care_patient`
--

CREATE TABLE `care_patient` (
  `careNumber` int(10) NOT NULL,
  `patientNumber` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `care_patient`
--

INSERT INTO `care_patient` (`careNumber`, `patientNumber`) VALUES
(987654321, 1234567890);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `patient`
--

CREATE TABLE `patient` (
  `patientNumber` int(11) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `birthdate` date NOT NULL,
  `phonenumber` int(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `patient`
--

INSERT INTO `patient` (`patientNumber`, `firstname`, `lastname`, `birthdate`, `phonenumber`, `email`, `password`) VALUES
(1234567890, 'Danny', 'de Jonge', '1996-10-17', 609876543, 'd@gmail.com', '123');

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `care`
--
ALTER TABLE `care`
  ADD PRIMARY KEY (`careNumber`);

--
-- Indexen voor tabel `care_patient`
--
ALTER TABLE `care_patient`
  ADD PRIMARY KEY (`careNumber`,`patientNumber`);

--
-- Indexen voor tabel `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`patientNumber`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

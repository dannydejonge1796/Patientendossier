-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 08 apr 2023 om 15:50
-- Serverversie: 10.4.27-MariaDB
-- PHP-versie: 8.2.0

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
-- Tabelstructuur voor tabel `allergy`
--

CREATE TABLE `allergy` (
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `allergy`
--

INSERT INTO `allergy` (`name`) VALUES
('Cosmetica-allergie'),
('Dierenallergie'),
('Hooikoorts'),
('Huisstofmijt'),
('Insectensteken'),
('Latexallergie'),
('Medicijnallergie'),
('Nikkelallergie'),
('Schimmelallergie'),
('Voedselallergie');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `allergy_patient`
--

CREATE TABLE `allergy_patient` (
  `allergy_name` varchar(255) NOT NULL,
  `patient_number` int(10) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `appointment`
--

CREATE TABLE `appointment` (
  `number` int(10) NOT NULL,
  `patient_number` int(10) NOT NULL,
  `care_number` int(10) NOT NULL,
  `care_lastname` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `date` date NOT NULL,
  `time` time NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `care`
--

CREATE TABLE `care` (
  `number` int(10) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `birthdate` date NOT NULL,
  `profession` varchar(255) NOT NULL,
  `phonenumber` int(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `care`
--

INSERT INTO `care` (`number`, `firstname`, `lastname`, `birthdate`, `profession`, `phonenumber`, `email`, `password`) VALUES
(1987654321, 'Danny', 'Care', '2023-04-17', 'dokter', 612345678, 'danny@gmail.com', '123');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `care_patient`
--

CREATE TABLE `care_patient` (
  `care_number` int(10) NOT NULL,
  `patient_number` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `care_patient`
--

INSERT INTO `care_patient` (`care_number`, `patient_number`) VALUES
(1987654321, 1234567890);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `health`
--

CREATE TABLE `health` (
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `health`
--

INSERT INTO `health` (`name`) VALUES
('Alzheimer'),
('Artritis'),
('Astma'),
('Beroerte'),
('Glaucoom'),
('Hartfalen'),
('Migraine'),
('Osteoporose'),
('Type 2 diabetes'),
('Ziekte van Parkinson');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `health_patient`
--

CREATE TABLE `health_patient` (
  `health_name` varchar(255) NOT NULL,
  `patient_number` int(10) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `medicine`
--

CREATE TABLE `medicine` (
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `medicine`
--

INSERT INTO `medicine` (`name`) VALUES
('Aspirine'),
('Diazepam'),
('Furosemide'),
('Ibuprofen'),
('Lorazepam'),
('Metformine'),
('Omeprazol'),
('Paracetamol'),
('Salbutamol'),
('Sertraline');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `medicine_patient`
--

CREATE TABLE `medicine_patient` (
  `patient_number` int(10) NOT NULL,
  `medicine_name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `dosage` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `patient`
--

CREATE TABLE `patient` (
  `number` int(10) NOT NULL,
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

INSERT INTO `patient` (`number`, `firstname`, `lastname`, `birthdate`, `phonenumber`, `email`, `password`) VALUES
(1234567890, 'Danny', 'de Jonge', '1996-10-17', 1234567890, 'd@gmail.com', '123');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `report`
--

CREATE TABLE `report` (
  `id` int(10) NOT NULL,
  `patient_number` int(10) NOT NULL,
  `report` text NOT NULL,
  `made_by` varchar(255) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `result`
--

CREATE TABLE `result` (
  `id` int(10) NOT NULL,
  `patient_number` int(10) NOT NULL,
  `result` text NOT NULL,
  `made_by` varchar(255) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `allergy`
--
ALTER TABLE `allergy`
  ADD PRIMARY KEY (`name`);

--
-- Indexen voor tabel `allergy_patient`
--
ALTER TABLE `allergy_patient`
  ADD PRIMARY KEY (`allergy_name`,`patient_number`),
  ADD KEY `allergy_patient_patient_number` (`patient_number`);

--
-- Indexen voor tabel `appointment`
--
ALTER TABLE `appointment`
  ADD PRIMARY KEY (`number`),
  ADD KEY `appointment_patient_number` (`patient_number`);

--
-- Indexen voor tabel `care`
--
ALTER TABLE `care`
  ADD PRIMARY KEY (`number`);

--
-- Indexen voor tabel `care_patient`
--
ALTER TABLE `care_patient`
  ADD PRIMARY KEY (`care_number`,`patient_number`);

--
-- Indexen voor tabel `health`
--
ALTER TABLE `health`
  ADD PRIMARY KEY (`name`);

--
-- Indexen voor tabel `health_patient`
--
ALTER TABLE `health_patient`
  ADD PRIMARY KEY (`health_name`,`patient_number`),
  ADD KEY `health_patient_patient_number` (`patient_number`);

--
-- Indexen voor tabel `medicine`
--
ALTER TABLE `medicine`
  ADD PRIMARY KEY (`name`);

--
-- Indexen voor tabel `medicine_patient`
--
ALTER TABLE `medicine_patient`
  ADD PRIMARY KEY (`patient_number`,`medicine_name`),
  ADD KEY `medicine_patient_medcine_name` (`medicine_name`);

--
-- Indexen voor tabel `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`number`);

--
-- Indexen voor tabel `report`
--
ALTER TABLE `report`
  ADD PRIMARY KEY (`id`),
  ADD KEY `report_patient_number` (`patient_number`);

--
-- Indexen voor tabel `result`
--
ALTER TABLE `result`
  ADD PRIMARY KEY (`id`),
  ADD KEY `result_patient_number` (`patient_number`);

--
-- Beperkingen voor geëxporteerde tabellen
--

--
-- Beperkingen voor tabel `allergy_patient`
--
ALTER TABLE `allergy_patient`
  ADD CONSTRAINT `allergy_patient_allergy_name` FOREIGN KEY (`allergy_name`) REFERENCES `allergy` (`name`),
  ADD CONSTRAINT `allergy_patient_patient_number` FOREIGN KEY (`patient_number`) REFERENCES `patient` (`number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `appointment`
--
ALTER TABLE `appointment`
  ADD CONSTRAINT `appointment_patient_number` FOREIGN KEY (`patient_number`) REFERENCES `patient` (`number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `health_patient`
--
ALTER TABLE `health_patient`
  ADD CONSTRAINT `health_patient_health_name` FOREIGN KEY (`health_name`) REFERENCES `health` (`name`),
  ADD CONSTRAINT `health_patient_patient_number` FOREIGN KEY (`patient_number`) REFERENCES `patient` (`number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `medicine_patient`
--
ALTER TABLE `medicine_patient`
  ADD CONSTRAINT `medicine_patient_medcine_name` FOREIGN KEY (`medicine_name`) REFERENCES `medicine` (`name`),
  ADD CONSTRAINT `medicine_patient_patient_number` FOREIGN KEY (`patient_number`) REFERENCES `patient` (`number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `report`
--
ALTER TABLE `report`
  ADD CONSTRAINT `report_patient_number` FOREIGN KEY (`patient_number`) REFERENCES `patient` (`number`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Beperkingen voor tabel `result`
--
ALTER TABLE `result`
  ADD CONSTRAINT `result_patient_number` FOREIGN KEY (`patient_number`) REFERENCES `patient` (`number`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

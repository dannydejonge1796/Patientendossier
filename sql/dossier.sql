-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Gegenereerd op: 27 mrt 2023 om 14:06
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
-- Tabelstructuur voor tabel `allergy`
--

CREATE TABLE `allergy` (
  `id` int(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `allergy`
--

INSERT INTO `allergy` (`id`, `name`, `description`) VALUES
(0, 'Hooikoorts', 'pollen van grassen, bomen en planten'),
(1, 'Huisstofmijt', 'mijten die in huisstof leven'),
(2, 'Dierenallergie', 'honden, katten, vogels en andere dieren'),
(3, 'Voedselallergie', 'bijvoorbeeld pinda\'s, schaaldieren of gluten'),
(4, 'Medicijnallergie', 'zoals penicilline of aspirine'),
(5, 'Insectensteken', 'bijen, wespen, muggen, etc.'),
(6, 'Schimmelallergie', 'schimmels die binnenshuis groeien'),
(7, 'Nikkelallergie', 'in sieraden, munten, etc.'),
(8, 'Cosmetica-allergie', 'huidverzorgingsproducten, parfums, etc.'),
(9, 'Latexallergie', 'latex handschoenen, ballonnen, etc.');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `allergy_patient`
--

CREATE TABLE `allergy_patient` (
  `allergy_id` int(10) NOT NULL,
  `patient_number` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `allergy_patient`
--

INSERT INTO `allergy_patient` (`allergy_id`, `patient_number`) VALUES
(0, 1234567890);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `care`
--

CREATE TABLE `care` (
  `number` int(10) NOT NULL,
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

INSERT INTO `care` (`number`, `firstname`, `lastname`, `profession`, `phonenumber`, `email`, `password`) VALUES
(987654321, 'Danny', 'Care', 'dokter', 612345678, 'danny@gmail.com', '123');

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
(987654321, 1234567890);

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `health`
--

CREATE TABLE `health` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `medicine`
--

CREATE TABLE `medicine` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `dosage` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Gegevens worden geëxporteerd voor tabel `medicine`
--

INSERT INTO `medicine` (`id`, `name`, `description`, `dosage`) VALUES
(0, 'Paracetamol', 'Een pijnstiller die vaak wordt gebruikt voor milde tot matige pijn', '500 mg'),
(1, 'Ibuprofen', 'Een ontstekingsremmende pijnstiller die vaak wordt gebruikt bij artritis en menstruatiepijn', '200 mg'),
(2, 'Aspirine', 'Een pijnstiller en koortsverlager die ook bloedverdunnende eigenschappen heeft', '500 mg'),
(3, 'Diazepam', 'Een kalmeringsmiddel dat wordt gebruikt om angst, slapeloosheid en spierkrampen te behandelen', '2-10 mg'),
(4, 'Lorazepam', 'Een kalmeringsmiddel dat wordt gebruikt om angst en slapeloosheid te behandelen', '1-4 mg'),
(5, 'Omeprazol', 'Een zuurremmer die wordt gebruikt voor de behandeling van maagzweren, reflux en indigestie', '20-40 mg'),
(6, 'Sertraline', 'Een antidepressivum dat wordt gebruikt voor de behandeling van depressie, angst en paniekstoornissen', '200 mg'),
(7, 'Metformine', 'Een medicijn voor diabetes type 2 dat de bloedsuikerspiegel verlaagt', '1000 mg'),
(8, 'Salbutamol', 'Een luchtwegverwijder die wordt gebruikt voor de behandeling van astma en chronische obstructieve longziekte (COPD)', '200 mic'),
(9, 'Furosemide', 'Een plasmiddel dat wordt gebruikt voor de behandeling van oedeem en hoge bloeddruk', '80 mg');

-- --------------------------------------------------------

--
-- Tabelstructuur voor tabel `medicine_patient`
--

CREATE TABLE `medicine_patient` (
  `patient_number` int(10) NOT NULL,
  `medicine_id` int(10) NOT NULL
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

--
-- Indexen voor geëxporteerde tabellen
--

--
-- Indexen voor tabel `allergy`
--
ALTER TABLE `allergy`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `allergy_patient`
--
ALTER TABLE `allergy_patient`
  ADD PRIMARY KEY (`allergy_id`,`patient_number`);

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
-- Indexen voor tabel `medicine`
--
ALTER TABLE `medicine`
  ADD PRIMARY KEY (`id`);

--
-- Indexen voor tabel `medicine_patient`
--
ALTER TABLE `medicine_patient`
  ADD PRIMARY KEY (`patient_number`,`medicine_id`);

--
-- Indexen voor tabel `patient`
--
ALTER TABLE `patient`
  ADD PRIMARY KEY (`number`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

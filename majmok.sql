-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2022. Dec 08. 17:04
-- Kiszolgáló verziója: 10.4.25-MariaDB
-- PHP verzió: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `java`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `majmok`
--

CREATE TABLE `majmok` (
  `id` int(11) NOT NULL,
  `fajta` varchar(32) COLLATE utf8mb4_hungarian_ci NOT NULL,
  `max_iq` tinyint(3) UNSIGNED NOT NULL COMMENT 'A majom fajtánál észrevett maximális iq szint.',
  `szereti_banant` tinyint(1) NOT NULL COMMENT 'A majomfajta egyedei szertik a banánt vagy sem.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_hungarian_ci;

--
-- A tábla adatainak kiíratása `majmok`
--

INSERT INTO `majmok` (`id`, `fajta`, `max_iq`, `szereti_banant`) VALUES
(1, 'Gorilla', 70, 1),
(2, 'Csimpánz', 100, 0);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `majmok`
--
ALTER TABLE `majmok`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `fajta` (`fajta`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `majmok`
--
ALTER TABLE `majmok`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

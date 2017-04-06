-- phpMyAdmin SQL Dump
-- version 4.0.10.12
-- http://www.phpmyadmin.net
--
-- Хост: 127.2.46.130:3306
-- Время создания: Апр 06 2017 г., 18:12
-- Версия сервера: 5.5.52
-- Версия PHP: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База данных: `module6`
--

-- --------------------------------------------------------

--
-- Структура таблицы `Actions`
--

CREATE TABLE IF NOT EXISTS `Actions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_2` (`id`),
  KEY `id` (`id`),
  KEY `id_3` (`id`),
  KEY `id_4` (`id`),
  KEY `id_5` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Дамп данных таблицы `Actions`
--

INSERT INTO `Actions` (`id`, `name`, `type`) VALUES
(1, 'action1', 'PRINT'),
(2, 'action2', 'PRINT_UPPER'),
(3, 'action3', 'PRINT');

-- --------------------------------------------------------

--
-- Структура таблицы `Conditions`
--

CREATE TABLE IF NOT EXISTS `Conditions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operation` varchar(100) DEFAULT NULL,
  `attribute` varchar(100) DEFAULT NULL,
  `value` varchar(100) DEFAULT NULL,
  `className` varchar(100) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `actionId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `actionId` (`actionId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=10 ;

--
-- Дамп данных таблицы `Conditions`
--

INSERT INTO `Conditions` (`id`, `operation`, `attribute`, `value`, `className`, `parentId`, `actionId`) VALUES
(2, 'OR', NULL, NULL, NULL, NULL, 1),
(3, NULL, 'color', '3', 'com.epam.jmp2017.model.conditions.impl.LengthCondition', 2, NULL),
(4, 'AND', NULL, NULL, NULL, 2, NULL),
(5, NULL, 'name', 'Doge', 'com.epam.jmp2017.model.conditions.impl.EqualsCondition', 4, NULL),
(6, NULL, 'color', '3', 'com.epam.jmp2017.model.conditions.impl.LengthCondition', 4, NULL),
(7, NULL, 'name', 'Doge', 'com.epam.jmp2017.model.conditions.impl.EqualsCondition', NULL, 2),
(8, NULL, 'color', '3', 'com.epam.jmp2017.model.conditions.impl.LengthCondition', NULL, 2),
(9, NULL, 'brand', 'LG', 'com.epam.jmp2017.model.conditions.impl.EqualsCondition', NULL, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `Dogs`
--

CREATE TABLE IF NOT EXISTS `Dogs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `color` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_DOG` (`name`,`color`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

-- --------------------------------------------------------

--
-- Структура таблицы `Fridges`
--

CREATE TABLE IF NOT EXISTS `Fridges` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `weight` varchar(100) DEFAULT NULL,
  `brand` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UNIQUE_FRIDGE` (`weight`,`brand`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `Conditions`
--
ALTER TABLE `Conditions`
  ADD CONSTRAINT `Conditions_ibfk_1` FOREIGN KEY (`actionId`) REFERENCES `Actions` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

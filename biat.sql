-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 12, 2017 at 04:17 AM
-- Server version: 5.6.17-log
-- PHP Version: 7.1.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mydb`
--

-- --------------------------------------------------------

--
-- Table structure for table `administrateur`
--

CREATE TABLE IF NOT EXISTS `administrateur` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  `prenom` varchar(45) DEFAULT NULL,
  `entreprise` int(11) DEFAULT NULL,
  `login` varchar(100) NOT NULL,
  `mdp` varchar(100) NOT NULL,
  PRIMARY KEY (`id_admin`),
  KEY `entreprise_idx` (`entreprise`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `administrateur`
--

INSERT INTO `administrateur` (`id_admin`, `nom`, `prenom`, `entreprise`, `login`, `mdp`) VALUES
(1, 'med naceur', 'El mabrouk', 1, 'nacer', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `agence`
--

CREATE TABLE IF NOT EXISTS `agence` (
  `id_agence` int(11) NOT NULL AUTO_INCREMENT,
  `nom_agence` varchar(45) DEFAULT NULL,
  `adresse` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_agence`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Dumping data for table `agence`
--

INSERT INTO `agence` (`id_agence`, `nom_agence`, `adresse`) VALUES
(1, 'AGENCE TUNIS', 'TUNIS 11 RUE DE PARIS');

-- --------------------------------------------------------

--
-- Table structure for table `agent`
--

CREATE TABLE IF NOT EXISTS `agent` (
  `id_agent` int(11) NOT NULL AUTO_INCREMENT,
  `code_agence` int(11) DEFAULT NULL,
  `login` varchar(45) DEFAULT NULL,
  `mdp` varchar(45) DEFAULT NULL,
  `mail` varchar(45) DEFAULT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `is_super` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_agent`),
  KEY `code_agence_idx` (`code_agence`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `agent`
--

INSERT INTO `agent` (`id_agent`, `code_agence`, `login`, `mdp`, `mail`, `nom`, `prenom`, `is_super`) VALUES
(1, 1, 'agent', '123456', 'agent@agent.com', 'agent', 'foulen', 0),
(2, 1, 'agt', '123456', 'ckqjs@cqkjc.com', 'super', 'agent', 1);

-- --------------------------------------------------------

--
-- Table structure for table `authorisateur entreprise`
--

CREATE TABLE IF NOT EXISTS `authorisateur entreprise` (
  `id_auth` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  `prenom` varchar(45) DEFAULT NULL,
  `departement` int(11) DEFAULT NULL,
  `login` varchar(100) NOT NULL,
  `mdp` varchar(100) NOT NULL,
  PRIMARY KEY (`id_auth`),
  KEY `departement_idx` (`departement`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `authorisateur entreprise`
--

INSERT INTO `authorisateur entreprise` (`id_auth`, `nom`, `prenom`, `departement`, `login`, `mdp`) VALUES
(2, 'fouzi', 'amara', 1, 'auth2', '123456');

-- --------------------------------------------------------

--
-- Table structure for table `compte`
--

CREATE TABLE IF NOT EXISTS `compte` (
  `id_compte` int(11) NOT NULL,
  `nom_commercial` varchar(45) DEFAULT NULL,
  `mnemonique` varchar(45) DEFAULT NULL,
  `nationnalité` varchar(45) DEFAULT NULL,
  `pays_residence` varchar(45) DEFAULT NULL,
  `qualité` varchar(45) DEFAULT NULL,
  `date_création` date DEFAULT NULL,
  `catégorie_client` varchar(45) DEFAULT NULL,
  `secteur_d'activité` varchar(45) DEFAULT NULL,
  `activité` varchar(45) DEFAULT NULL,
  `forme_juridique` varchar(45) DEFAULT NULL,
  `code_situation_juridique` varchar(45) DEFAULT NULL,
  `date_situation_juridique` date DEFAULT NULL,
  `capital_sociale` mediumtext,
  `chiffre_d'affaire` mediumtext,
  `annee_chiffre_d'affaire` int(11) DEFAULT NULL,
  `nbr_employer` int(11) DEFAULT NULL,
  `statut` varchar(45) DEFAULT NULL,
  `segment` varchar(45) DEFAULT NULL,
  `polygestion` varchar(45) DEFAULT NULL,
  `charge_clientele` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_compte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `compte client`
--

CREATE TABLE IF NOT EXISTS `compte client` (
  `id_compte` int(11) NOT NULL,
  `nom_agence` varchar(45) DEFAULT NULL,
  `nom_donneur_d'ordre` varchar(45) DEFAULT NULL,
  `adresse_donneur_d'ordre` varchar(45) DEFAULT NULL,
  `numéro_compte` int(11) DEFAULT NULL,
  `code_devise` int(11) DEFAULT NULL,
  `num_code_douane` int(11) DEFAULT NULL,
  `num_registre_commerce` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_compte`),
  KEY `numéro_compte_idx` (`numéro_compte`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `departement entreprise`
--

CREATE TABLE IF NOT EXISTS `departement entreprise` (
  `id_departement` int(11) NOT NULL AUTO_INCREMENT,
  `nom_departement` varchar(45) DEFAULT NULL,
  `entreprise` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_departement`),
  KEY `entreprise_idx` (`entreprise`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `departement entreprise`
--

INSERT INTO `departement entreprise` (`id_departement`, `nom_departement`, `entreprise`) VALUES
(1, 'Informatique', 1),
(2, 'Informatique', 2),
(3, 'Resource humaine', 1),
(4, 'Finance', 1);

-- --------------------------------------------------------

--
-- Table structure for table `entreprise`
--

CREATE TABLE IF NOT EXISTS `entreprise` (
  `id_entreprise` int(11) NOT NULL AUTO_INCREMENT,
  `nom_entreprise` varchar(45) DEFAULT NULL,
  `adresse` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_entreprise`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `entreprise`
--

INSERT INTO `entreprise` (`id_entreprise`, `nom_entreprise`, `adresse`) VALUES
(1, 'entreprise 1', 'tunis'),
(2, 'entreprise', 'Tunis');

-- --------------------------------------------------------

--
-- Table structure for table `fournisseur`
--

CREATE TABLE IF NOT EXISTS `fournisseur` (
  `id_ fournisseur` int(11) NOT NULL,
  `nom_commercial` varchar(45) DEFAULT NULL,
  `adresse` varchar(45) DEFAULT NULL,
  `adresse_banque` varchar(45) DEFAULT NULL,
  `nom_banque` varchar(45) DEFAULT NULL,
  `ville` varchar(45) DEFAULT NULL,
  `pays` varchar(45) DEFAULT NULL,
  `swift` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_ fournisseur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fournisseur`
--

INSERT INTO `fournisseur` (`id_ fournisseur`, `nom_commercial`, `adresse`, `adresse_banque`, `nom_banque`, `ville`, `pays`, `swift`) VALUES
(1, 'fournisseur 1', 'adresse fournisseur', 'adresse banque', 'nom banque', 'ville banque', 'pays', 'swift');

-- --------------------------------------------------------

--
-- Table structure for table `reglement facture`
--

CREATE TABLE IF NOT EXISTS `reglement facture` (
  `id_reglement` int(11) NOT NULL AUTO_INCREMENT,
  `statut` varchar(45) NOT NULL,
  `date_validation` date NOT NULL,
  `date_authorisation` date DEFAULT NULL,
  `date_confirmation_agence` date DEFAULT NULL,
  `montant lettre` varchar(45) DEFAULT NULL,
  `montant chiffre` varchar(100) DEFAULT NULL,
  `devise` varchar(45) DEFAULT NULL,
  `motif_payement` varchar(45) DEFAULT NULL,
  `frais_commission_BIAT` varchar(45) DEFAULT NULL,
  `frais_correspondant` varchar(45) DEFAULT NULL,
  `type_payement` varchar(45) NOT NULL DEFAULT 'SWIFT',
  `observation` varchar(45) DEFAULT NULL,
  `authorisation_entreprise` int(11) DEFAULT NULL,
  `validation_entreprise` int(11) DEFAULT NULL,
  `agent` int(11) DEFAULT NULL,
  `titre_commerce_extrieurs` int(11) DEFAULT NULL,
  `nom_donneur_d'ordre` varchar(45) DEFAULT NULL,
  `adresse_donneur_d'ordre` varchar(45) DEFAULT NULL,
  `num_compte` varchar(100) DEFAULT NULL,
  `code_devise` varchar(100) DEFAULT NULL,
  `num_code_douane` varchar(100) DEFAULT NULL,
  `num_registre_commerce` varchar(100) DEFAULT NULL,
  `fournisseur` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_reglement`),
  KEY `authorisation_entreprise_idx` (`authorisation_entreprise`),
  KEY `validation_entreprise_idx` (`validation_entreprise`),
  KEY `agent_idx` (`agent`),
  KEY `titre_commerce_extrieurs_idx` (`titre_commerce_extrieurs`),
  KEY `fournisseur_idx` (`fournisseur`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `reglement facture`
--

INSERT INTO `reglement facture` (`id_reglement`, `statut`, `date_validation`, `date_authorisation`, `date_confirmation_agence`, `montant lettre`, `montant chiffre`, `devise`, `motif_payement`, `frais_commission_BIAT`, `frais_correspondant`, `type_payement`, `observation`, `authorisation_entreprise`, `validation_entreprise`, `agent`, `titre_commerce_extrieurs`, `nom_donneur_d'ordre`, `adresse_donneur_d'ordre`, `num_compte`, `code_devise`, `num_code_douane`, `num_registre_commerce`, `fournisseur`) VALUES
(1, 'confirme', '2017-05-01', '2017-05-03', '2017-05-09', 'montant', '45.6587', 'gfgh', 'hgfhg', 'hgfhg', 'hghgfhg', 'SWIFT', NULL, NULL, 1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(2, 'rejete', '2017-05-02', '2017-05-03', NULL, 'ghbh', 'fgvffv', 'fgvgg', 'fbbbgf', 'dcvb', 'fgbhf', 'xcbb', 'xcbddvg fgvgg fgcvvg', NULL, 1, 1, 1, '', '', 'dcbccvv', 'xvbbcc', 'fhbn', 'fgbbn', 1),
(3, 'confirme', '2017-05-02', NULL, '2017-05-09', 'tff', 'ggff', 'ccvv', 'cccv', 'cvv', 'ggv', 'ggvv', 'ccvvv', NULL, 1, 2, 1, '', '', 'vvvv', 'ggvh', 'ccvv', 'ccv', 1),
(4, 'rejete', '2017-05-02', '2017-05-09', NULL, 'jfjfj', 'hhfhfh', 'hxhfh', 'jxjfjf', 'hxjfjf', 'xjjfjf', 'jjjff', 'hhjjf', 2, 1, 1, 1, 'ghjjd', 'hhjjf', 'hfjfj', 'fhjfjf', 'hfhfjf', 'hjfjfk', 1);

-- --------------------------------------------------------

--
-- Table structure for table `rejet`
--

CREATE TABLE IF NOT EXISTS `rejet` (
  `id_rejet` int(11) NOT NULL AUTO_INCREMENT,
  `motif_rejet` varchar(45) NOT NULL,
  `agent` int(11) NOT NULL,
  `reglement` int(11) NOT NULL,
  PRIMARY KEY (`id_rejet`),
  KEY `agent_idx` (`agent`),
  KEY `reglement_facture_idx` (`reglement`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

--
-- Dumping data for table `rejet`
--

INSERT INTO `rejet` (`id_rejet`, `motif_rejet`, `agent`, `reglement`) VALUES
(1, 'motif reg', 1, 2),
(2, 'fcvhh fghhv', 1, 2),
(3, 'motif', 1, 4);

-- --------------------------------------------------------

--
-- Table structure for table `titre commerce extrieurs`
--

CREATE TABLE IF NOT EXISTS `titre commerce extrieurs` (
  `id_tce` int(11) NOT NULL,
  `code_titre` varchar(45) DEFAULT NULL,
  `numéro` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`id_tce`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `titre commerce extrieurs`
--

INSERT INTO `titre commerce extrieurs` (`id_tce`, `code_titre`, `numéro`, `date`) VALUES
(1, '555sdgsg5dgh', 45, '2017-05-02');

-- --------------------------------------------------------

--
-- Table structure for table `utilisateur entreprise`
--

CREATE TABLE IF NOT EXISTS `utilisateur entreprise` (
  `id_user` int(11) NOT NULL,
  `e-mail` varchar(45) DEFAULT NULL,
  `mdp` varchar(45) DEFAULT NULL,
  `num_tel` varchar(45) DEFAULT NULL,
  `statut` varchar(45) DEFAULT NULL,
  `date_creation_profil` varchar(45) DEFAULT NULL,
  `departement_entreprise` int(11) DEFAULT NULL,
  `login` varchar(100) NOT NULL,
  PRIMARY KEY (`id_user`),
  KEY `departement_entreprise_idx` (`departement_entreprise`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `validateur entreprise`
--

CREATE TABLE IF NOT EXISTS `validateur entreprise` (
  `id_val` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(45) DEFAULT NULL,
  `prenom` varchar(45) DEFAULT NULL,
  `departement` int(11) DEFAULT NULL,
  `login` varchar(100) NOT NULL,
  `mdp` varchar(100) NOT NULL,
  PRIMARY KEY (`id_val`),
  KEY `departement_idx` (`departement`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `validateur entreprise`
--

INSERT INTO `validateur entreprise` (`id_val`, `nom`, `prenom`, `departement`, `login`, `mdp`) VALUES
(1, 'foulen', 'ben falten', 1, 'val', '123456'),
(2, 'Houssem eddine', 'mrad', 3, 'val2', '123456');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `administrateur`
--
ALTER TABLE `administrateur`
  ADD CONSTRAINT `;xcv,s ;sd;vsdvsv` FOREIGN KEY (`entreprise`) REFERENCES `entreprise` (`id_entreprise`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `agent`
--
ALTER TABLE `agent`
  ADD CONSTRAINT `KHSQDBKQDK` FOREIGN KEY (`code_agence`) REFERENCES `agence` (`id_agence`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `authorisateur entreprise`
--
ALTER TABLE `authorisateur entreprise`
  ADD CONSTRAINT `kjkjkj` FOREIGN KEY (`departement`) REFERENCES `departement entreprise` (`id_departement`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `compte client`
--
ALTER TABLE `compte client`
  ADD CONSTRAINT `numéro_compte` FOREIGN KEY (`numéro_compte`) REFERENCES `compte` (`id_compte`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `departement entreprise`
--
ALTER TABLE `departement entreprise`
  ADD CONSTRAINT `vddfvsdfvsd` FOREIGN KEY (`entreprise`) REFERENCES `entreprise` (`id_entreprise`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `reglement facture`
--
ALTER TABLE `reglement facture`
  ADD CONSTRAINT `qckjqskjckjq` FOREIGN KEY (`agent`) REFERENCES `agent` (`id_agent`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `fournisseur` FOREIGN KEY (`fournisseur`) REFERENCES `fournisseur` (`id_ fournisseur`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `jshsfhj` FOREIGN KEY (`validation_entreprise`) REFERENCES `validateur entreprise` (`id_val`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `titre_commerce_extrieurs` FOREIGN KEY (`titre_commerce_extrieurs`) REFERENCES `titre commerce extrieurs` (`id_tce`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `x;cvs;dv` FOREIGN KEY (`authorisation_entreprise`) REFERENCES `authorisateur entreprise` (`id_auth`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Constraints for table `rejet`
--
ALTER TABLE `rejet`
  ADD CONSTRAINT `qkdkqjsckcq` FOREIGN KEY (`agent`) REFERENCES `agent` (`id_agent`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `qkfjhkqsjhdqs` FOREIGN KEY (`reglement`) REFERENCES `reglement facture` (`id_reglement`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `validateur entreprise`
--
ALTER TABLE `validateur entreprise`
  ADD CONSTRAINT `ytfhgfhg` FOREIGN KEY (`departement`) REFERENCES `departement entreprise` (`id_departement`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

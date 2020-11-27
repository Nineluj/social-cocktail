-- MySQL dump 10.13  Distrib 8.0.22, for Linux (x86_64)
--
-- Host: localhost    Database: social_cocktail_db
-- ------------------------------------------------------
-- Server version	8.0.22

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Glass`
--

DROP TABLE IF EXISTS `Glass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Glass` (
  `id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(1024) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Glass`
--

LOCK TABLES `Glass` WRITE;
/*!40000 ALTER TABLE `Glass` DISABLE KEYS */;
INSERT INTO `Glass` VALUES (1,'Highball glasses are tall, 8 or 9 fl oz (240 or 270 mL) glasses designed to hold highball drinks — iced drinks containing liquor along with water and/or a carbonated mixer. Highball glasses may in some parts also be known as a cooler or a slim jim.','Highball glass'),(2,'A cocktail glass is a stemmed glass with an inverted cone bowl, mainly used to serve straight-up cocktails. The term cocktail glass is often used interchangeably with martini glass, despite their differing slightly.','Cocktail glass'),(3,'The Old Fashioned glass, rocks glass, lowball glass (or simply lowball), is a short tumbler used for serving spirits, such as whisky, neat or with ice cubes (\"on the rocks\"). It is also normally used to serve certain cocktails, such as the Old Fashioned, from which it receives its name.','Old-fashioned glass'),(4,'A collins glass is a glass tumbler which typically will contain 300 to 410 millilitres. It is used to serve mixed drinks, especially Tom Collins or John Collins cocktails. It is cylindrical in shape and narrower and taller than a highball glass','Collins glass'),(5,'By the end of the 19th century the Pousse Café had its own glass: still stemmed, but with the V of the sherry glass greatly tightened so that the glass wouldn’t hold more than 1 ounce—sufficient for a drink where the ingredients were measured in barspoons. The result is a small, narrow glass that perfectly shows off the layers of liqueur. The capacity varies, but the standard size is generally 1 ½ ounce.','Pousse cafe glass'),(6,'The champagne flute (French: flûte à Champagne) is a stem glass with either a tall tapered conical shape or elongated slender bowl, generally holding about 6 to 10 US fl oz (180 to 300 ml) of liquid. The champagne flute was developed along with other wine stemware in the early 1700s as the preferred shape for sparkling wine as materials for drinking vessels shifted from metal and ceramic to glassware. Initially, the flute was tall, conical, and slender; by the 20th century, preferences changed from a straight-sided glass to one which curved inward slightly near the lip','Champagne flute'),(7,'A sour glass (also known as a delmonico glass) has a rounded cup with a stem specified for sweet, citrus drinks, such as a Whiskey Sour. The stem helps keep a cold drink from the warmth of your hand.','Whiskey sour glass'),(8,'A snifter (also called brandy snifter, brandy bowl, cognac glass, or balloon) is a type of stemware, a short-stemmed glass whose vessel has a wide bottom and a relatively narrow top. It is mostly used to serve aged brown spirits such as bourbon, brandy, and whisky.','Brandy snifter'),(9,'White wine glasses vary enormously in size and shape, from the delicately tapered Champagne flute, to the wide and shallow glasses used to drink Chardonnay. Different shaped glasses are used to accentuate the unique characteristics of different styles of wine. Wide mouthed glasses function similarly to red wine glasses discussed above, promoting rapid oxidation which alters the flavor of the wine. White wines which are best served slightly oxidized are generally full flavored wines, such as oaked chardonnay. For lighter, fresher styles of white wine, oxidation is less desirable as it is seen to mask the delicate nuances of the wine. To preserve a crisp, clean flavor, many white wine glasses will have a smaller mouth, which reduces surface area and in turn, the rate of oxidization. In the case of sparkling wine, such as Champagne or Asti, an even smaller mouth is used to keep the wine sparkling longer in the glass.','White wine glass'),(10,'The Nick and Nora glass is a stemmed glass that is used for serving stirred or shaken cocktails. It can be used for pretty much any drink that would otherwise be served in a Martini glass or coupe glass, though it most commonly sports spirit-forward drinks without citrus—at least that seems to be the trend in recent years.','Nick and Nora Glass'),(11,'A Hurricane glass is a form of drinking glass which typically will contain 20 US fluid ounces (590 ml; 21 imp fl oz). It is used to serve mixed drinks, particularly the Hurricane from which it is named originating at Pat O\'Brien\'s Bar in New Orleans.','Hurricane glass'),(12,'A mug is a type of cup typically used for drinking hot beverages, such as coffee, hot chocolate, soup, or tea. Mugs usually have handles and hold a larger amount of fluid than other types of cup. Usually a mug holds approximately 8-12 US fluid ounces (350 ml) of liquid; double a tea cup. A mug is a less formal style of drink container and is not usually used in formal place settings, where a teacup or coffee cup is preferred.','Coffee mug'),(13,'A shot glass is a small glass originally designed to hold or measure spirits or liquor, which is either imbibed straight from the glass (\"a shot\") or poured into a cocktail (\"a drink\"). An alcoholic beverage served in a shot glass and typically consumed quickly, in one gulp, may also be known as a \"shooter\".','Shot glass'),(14,'Jar Jar Binks was a Gungan male military commander and politician who played a key role in the Invasion of Naboo and the Clone Wars that culminated in the fall of the Galactic Republic and the rise of the Galactic Empire. (This is a joke, only one drink uses this glass)','Jar'),(15,'The multi-purpose Irish coffee mug serves up its namesake, along with tea or cocoa, in tempered glass updated with modern, straight-sided lines.','Irish coffee cup'),(16,'A large bowl used for serving and keeping a alcoholic juice drinks called punch. Typically used in America.','Punch bowl'),(17,'A container with a spout used for storing and pouring liquids. In English-speaking countries outside North America, a jug is any container with a handle and a mouth and spout for liquid — American \"pitchers\" will be called jugs elsewhere. Generally a pitcher also has a handle, which makes pouring easier.','Pitcher'),(18,'A standard, all-purpose beer glass with slightly tapered walls. Used primarily for English- and American-style lagers and ales ranging from light lagers to imperial stouts. Pint glasses come in two sizes: Imperial 20 ounce (570 mL) or US 16 ounce (470 mL) pints.','Pint glass'),(19,'They are mugs made out of copper. Copper mugs are used for serving Moscow Mule because they are ideal for maintaining the cool temperature of your drink.','Copper Mug'),(20,'Glasses that have designs are based on the theory that wine can be delivered via the shape of the bowl to the taste buds in a way to accentuate the flavors specific to the grape variety or common to a standard blend. For blends that do not have a specifically designed glass the style designed for the predominate varietal in the blend is often used. ','Wine Glass'),(21,'Has straight sides and is designed for drinks prepared using the pousse-café method.','Cordial glass'),(22,'A German-style mug, often of great volume, with handles and thick walls to help maintain a cool temperature. An earthenware, ceramic, or metal version is called a stein.','Beer mug'),(23,'A stemmed glass with a curved bowl used mostly for serving its namesake drink. Now burdened with a reputation as a cheap, plastic method for delivering sugary, pre-mixed Margaritas at Tex-Mex bars, the margarita glass was once identified with a nobler tradition, called a coupette for its resemblance to a coupe.','Margarita\\/Coupette glass'),(24,'A long, narrow glasses with walls that taper towards the base. Used to consolidate volatiles and support delicate heads of pilseners and other lagers.','Beer pilsner'),(25,'A standard, all-purpose beer glass with slightly tapered walls. Used primarily for English- and American-style lagers and ales ranging from light lagers to imperial stouts. Pint glasses come in two sizes: Imperial 20 ounce (570 mL) or US 16 ounce (470 mL) pints.','Beer Glass'),(26,'A tall narrow glass with a short stem. Other uses include keeping deserts.','Parfait glass'),(27,'A molded glass jar typically used in home canning to preserve food. The cooling of the contents creates a vacuum in the head space, pulling the lid into tight contact with the jar rim to create a hermetic seal. Once cooled, the band is removed to prevent residual water between the jar threads and the lid from rusting the band.','Mason jar'),(28,'A stemmed glass with a curved bowl used mostly for serving its namesake drink. Now burdened with a reputation as a cheap, plastic method for delivering sugary, pre-mixed Margaritas at Tex-Mex bars, the margarita glass was once identified with a nobler tradition, called a coupette for its resemblance to a coupe.','Margarita glass'),(29,'A martini glass is a stemmed glass with an inverted cone bowl, mainly used to serve straight-up cocktails.','Martini Glass'),(30,'Similar to a wine glass, the balloon glass has a shorter stem and a wider bowl. The purpose is opposite of the wine glass — the balloon glass is designed to be cupped in the hand to warm the brandy.','Balloon Glass'),(31,'The coupe is a stemmed glass featuring a broad, shallow bowl. As you may have guessed, this glass was originally developed for champagne, however changing tastes have replaced it with the fluted glass as the go-to glass for champagne drinkers.','Coupe Glass');
/*!40000 ALTER TABLE `Glass` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-11-27 17:16:47
<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>

	<xsl:template match="/">
		<html>
			<head>
				<title>Countries of the world</title>
			</head>
			<body style="background-color:white;">
				<h1>Information about the countries</h1> 
				Styled by: Arnaud Binome 1, Hung Binome 2 (BJSP)

				<!-- Afficher les métadonnées -->
				<xsl:apply-templates select="metadonnees"/>

				<!-- Pays parlant plus de 2 langues -->
				<h1>Countries with more than 2 spoken languages</h1>
				<ul>
					<xsl:apply-templates select="//country[count(languages/*) > 2]" mode="languages"/>
				</ul>

				<!-- Boucle sur les continents uniques -->
				<xsl:for-each select="//infosContinent/continent[not(. = preceding::continent)]">
					<h2 style="color:black;">
						<xsl:value-of select="."/>
					</h2>

					<!-- Boucle sur les sous-continents -->
					<xsl:for-each select="//infosContinent[continent = current()]/subregion[not(. = preceding::subregion)]">
						<h3 style="color:blue;">
							<xsl:value-of select="."/> 
							( <xsl:value-of select="count(//country[infosContinent/subregion = current()])"/> countries )
						</h3>

						<!-- Générer un tableau pour chaque sous-continent -->
						<table border="3" width="100%" align="center">
							<tr>
								<th>#</th>
								<th>Name</th>
								<th>Capital</th>
								<th>Coordinates</th>
								<th>Neighbors</th>
								<th>Flag</th>
							</tr>
							
							<!-- Appliquer le template "country" pour chaque pays de ce sous-continent -->
							<xsl:apply-templates select="//country[infosContinent/subregion = current()]"/>
						</table>
					</xsl:for-each>
				</xsl:for-each>
			</body>
		</html>
	</xsl:template>

	<!-- Template pour afficher chaque pays dans les tableaux -->
	<xsl:template match="country">
		<tr>
			<td>
				<xsl:number value="position()"/>
			</td>
			<td>
				<span style="color:green">
					<xsl:value-of select="country_name/offic_name"/>
				</span>
				(<xsl:value-of select="country_name/common_name"/>)
				<br/>
				<span style="color:blue">
					<xsl:value-of select="country_name/native_name[@lang='fra']/offic_name"/>
				</span>
			</td>
			<td>
				<xsl:value-of select="capital"/>
			</td>
			<td>
				latitude: <xsl:value-of select="coordinates/@lat"/>
				<br/>
				longitude: <xsl:value-of select="coordinates/@long"/>
			</td>
			<td>
				<xsl:if test="count(borders/neighbour) != 0">
					<xsl:apply-templates select="borders"/>
				</xsl:if>
				<xsl:if test="count(borders/neighbour) = 0 and landlocked = 'false'">
					Island
				</xsl:if>
			</td>
			<td>
				<img src="{concat('http://www.geonames.org/flags/x/',translate(country_codes/cca2, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'),'.gif')}" alt="" height="40" width="60"/>
			</td>
		</tr>
	</xsl:template>

	<!-- Template pour afficher les voisins -->
	<xsl:template match="borders">
		<xsl:for-each select="neighbour">
			<xsl:variable name="CCA3" select="."/>
			<xsl:variable name="NeighbourCountry" select="//country[country_codes/cca3=$CCA3]"/>
			<xsl:value-of select="$NeighbourCountry/country_name/common_name"/>
			<xsl:if test="position() != last()">, </xsl:if>
		</xsl:for-each>
	</xsl:template>

	<!-- Affichage des pays avec plus de 2 langues -->
	<xsl:template match="country" mode="languages">
		<li>
			<xsl:value-of select="country_name/common_name"/> :
			<xsl:for-each select="languages/*">
				<xsl:value-of select="."/> (<xsl:value-of select="name()"/>)
				<xsl:if test="position() != last()"> , </xsl:if>
			</xsl:for-each>
		</li>
	</xsl:template>

	<!-- Template pour afficher les métadonnées -->
	<xsl:template match="metadonnees">
		<p style="text-align:center; color:green;">
			Objectif : <xsl:value-of select="objectif"/>
		</p>
		<hr/>
	</xsl:template>

</xsl:stylesheet>

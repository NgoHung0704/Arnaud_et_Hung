<?xml version = "1.0" encoding = "UTF-8"?>
<!--
	 TP DW 3IF 2025, Elöd EGYED-ZSIGMOND 
-->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html"/>

	<xsl:template match="/"> <!-- match="/" -> for whole document -->
		<html>
			<head>
				<title> Arnaud MALLE, Anh Hung NGO </title>
			</head>

			<body style="background-color:white;">
				<h1>Information about vélov Stations in Lyon Metropolitan Area</h1> 
 				Styled by: Arnaud MALLE, Anh Hung NGO (B3XXX)
                <hr/>

				<table border="3" width="100%" align="center">
					<tr>
						<th>1. Nb</th>
						<th>2. Commune</th>
						<th>3. Velov station count</th>
						<th>4. Station with the most available bikes</th>
						<th>5. Station with the most bikestands</th>
					</tr>

					
					<xsl:for-each select="//velovstation/commune[not(. = preceding::commune)]">
						<xsl:sort select="." data-type="text" order="ascending"/>
							<tr>
								<td><xsl:value-of select="position()"/></td>
								<td><xsl:value-of select="current()"/></td>
								<td><xsl:value-of select="count(//velovstation[commune = current()])"/></td>
								
								<td>
									<xsl:variable name="maxStation1">
										<xsl:for-each select="//velovstation[commune = current()]">
											<xsl:sort select="available_bikes" data-type="number" order="descending"/>
											<xsl:if test="position() = 1">
												<xsl:value-of select="name"/>
											</xsl:if>
										</xsl:for-each>
									</xsl:variable>
									<xsl:value-of select="$maxStation1"/> : <span style="color:blue;"><xsl:value-of select="//velovstation[name = $maxStation1]/available_bikes"/></span>
								</td>

								<td>
									<xsl:variable name="maxStation2">
										<xsl:for-each select="//velovstation[commune = current()]">
											<xsl:sort select="bike_stands" data-type="number" order="descending"/>
											<xsl:if test="position() = 1">
												<xsl:value-of select="name"/>
											</xsl:if>
										</xsl:for-each>
									</xsl:variable>
									<xsl:value-of select="$maxStation2"/> : <span style="color:blue;"><xsl:value-of select="//velovstation[name = $maxStation2]/bike_stands"/></span>
								</td>
							</tr>
					</xsl:for-each>
				
                </table>
			</body>
		</html>
	</xsl:template>


</xsl:stylesheet>
<!-- example.xsl -->
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" indent="yes" encoding="UTF-8" />

	<xsl:template match="/">
		<xsl:for-each select="data/item">
			<p>
				<xsl:value-of select="name" />
				<br />
				<xsl:value-of select="description" />
			</p>
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>
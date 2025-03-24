document.getElementById("button5").addEventListener("click", function() {   
    Bouton5_affichage("velov.xml", "velovToTablehtml.xsl");
});


//////////////////////////////////////////////////////////////////////////////////////////
function Bouton5_affichage(xmlDocumentUrl, xslDocumentUrl) {
    // Load the XSL file using a synchronous XMLHttpRequest
    var xslDocument = loadHTTPXML(xslDocumentUrl);

    // Create an XSL processor
    var xsltProcessor = new XSLTProcessor();

    // Import the .xsl file
    xsltProcessor.importStylesheet(xslDocument);

    // Load the XML file using a synchronous XMLHttpRequest
    var xmlDocument = loadHTTPXML(xmlDocumentUrl);

    // Create the XML document transformed by XSL
    var newXmlDocument = xsltProcessor.transformToDocument(xmlDocument);

    // Find the parent (whose ID is "here") of the element to be replaced in the current HTML document
    var elementHtmlParent = window.document.getElementById("id_element_to_replace");

    // Insert the transformed element into the HTML page
    elementHtmlParent.innerHTML = newXmlDocument.children[0].innerHTML;
}

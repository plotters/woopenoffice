# Connecting to the OOo service #

```
OOService oos = new OOService();
oos.openConnection("localhost", 8100);
```

No need to close the connection.

# Creating a text document, write two paragraphs to the document and save it as a PDF #

```
OOTextDocument document = new OOTextDocument(oos.mxComponentLoader());
document.create();
document.load();
document.insertPlainText("Bonjour");
document.insertParagraphBreak();
document.appendParagraph();
document.insertPlainText("Hello");
document.exportToPDF("file:///tmp/bidon.pdf", true);
document.close();
```

# Import a Word (.doc) document and export it as PDF #

```
OOTextDocument document = new OOTextDocument(oos.mxComponentLoader());
document.setDocumentUrl("file:///Users/probert/Documents/cv-pascal-robert.doc");
document.load();
document.exportToPDF("file:///tmp/cv-pascal-robert.pdf", true);
document.close();
```
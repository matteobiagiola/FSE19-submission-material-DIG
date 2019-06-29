Contains the navigation graphs in dot format for the subject systems. The graphs are obtained using the [poparsing](https://github.com/matteobiagiola/FSE19-submission-material-DIG/tree/master/poparsing) project.

For each application there are two files:
- the first one, named `<application_name>.txt` is the navigation graph extracted when the POs for the subject application are created manually
- the second one, named `<application_name>-apogen.txt` is the navigation graph extracted when the POs for the subject application are created automatically using the tool [Apogen](https://github.com/tsigalko18/apogen)

Navigation graphs are used during test generation. Test sequences are created by traversing the graphs where each edge is a PO method and each node is a PO.

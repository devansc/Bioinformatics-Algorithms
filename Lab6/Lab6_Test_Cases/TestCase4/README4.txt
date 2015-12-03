README

Database is a single fasta.

The Query is a file of similar length to the database file.  There are multiple amino acid differences between the Query and the database.  However, they are very similar overall.

Gap Penalty=5

Expected output:
One alignment scores should be output.  Should include the name of the database sequence (from the first line (>) of the fasta) and the alignment score (numeric)
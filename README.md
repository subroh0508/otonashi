# Otonashi

[ ![Download](https://api.bintray.com/packages/subroh0508/maven/Otonashi/images/download.svg) ](https://bintray.com/subroh0508/maven/Otonashi/_latestVersion)
![KOTORI-rate](https://img.shields.io/badge/dynamic/json.svg?label=Kotori-Rate&colorB=fff98e&query=$.kotori_rate&uri=https%3A%2F%2Fus-central1-kotori-badge.cloudfunctions.net%2Fbadge%3Fowner%3Dsubroh0508%26repo%3Dotonashi&suffix=%)

Otonashi is a SPARQL client library made by Kotlin.

features

- SPARQL query builder
- Gradle Plugin generating Kotlin code for SPARQL vocabularies from specified endpoint(alpha)
- Includes SPARQL vocabulary classes
  - RDF: https://www.w3.org/1999/02/22-rdf-syntax-ns
  - RDF-Schema: http://www.w3.org/2000/01/rdf-schema
  - Schema.org: https://schema.org
  - foaf: http://xmlns.com/foaf/spec/20140114.html
  - Im@sparql: https://sparql.crssnky.xyz/imas/
  
## Install

```
repositories {
    jcenter()
    // or maven { url 'https://dl.bintray.com/subroh0508/maven' }
}

dependencies {
    implementation 'net.subroh0508.otonashi:core:${latest_version}'
    
    // additional
    implementation 'net.subroh0508.otonashi.vocabularies:schema:${latest_version}'
    implementation 'net.subroh0508.otonashi.vocabularies:foaf:${latest_version}'
    implementation 'net.subroh0508.otonashi.vocabularies:imasparql:${latest_version}'
}
```

## Usage

Sample query is

```
SELECT
  (replace(str(?s), 'https:\/\/sparql.crssnky.xyz\/imasrdf\/RDFs\/detail\/', '') as ?id)
  ?name
  (concat('[',group_concat(?unit_name;separator=','),']') as ?unit_names)
WHERE {
  ?s rdf:type imas:Idol;
     schema:name ?name;
     imas:Title ?title;
     schema:memberOf ?unit_url
  filter(contains(?title, 'CinderellaGirls')).
  ?unit_url rdf:type imas:Unit;
    schema:name ?unit_name.
} group by ?s ?name
```

Use by Otonashi

```
// initialize client
// note: requires schema, foaf and imasparql vocabularies
val kotori: Kotori by lazy {
    Otonashi.Study {
        destination("https://sparql.crssnky.xyz/spql/imas/query")
        reminds(SchemaPrefix.SCHEMA, FoafPrefix.FOAF, ImasparqlPrefix.IMAS)
        buildsUp(*schemaVocabularies, *foafVocabularies, *imasparqlVocabularies)
    }
}

// build query
val query = kotori.where {
    v("s") be {
        rdfP.type to imasC.idol and
        schemaP.name to v("name") and
        imasP.title to v("title") and
        schemaP.memberOf to v("unit_url")
    }
    filter {
        contains(v("title"), "CinderellaGirls")
    }
    v("unit_url") be {
        rdfP.type to imasC.unit and
        schemaP.name to v("unit_name")
    }
}.select {
    replace(
        str(v("s")),
        """https://sparql.crssnky.xyz/imasrdf/RDFs/detail/""",
        ""
    ) `as` v("id")
    groupConcat(v("unit_name"), ",") `as` v("unit_names")

    + v("id") + v("name") + v("unit_names")
}.groupBy { + v("s") + v("name") }

print(query.toString()) // => "SELECT (replace(..."
```

## Special Thanks

im@sparql: <https://github.com/imas/imasparql>

## License

This project is licensed under the terms of the
[MIT license](/LICENSE).

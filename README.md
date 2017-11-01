# fuber-api

Backend for the fuber app

## Prerequisites

You will need [Leiningen][1] 2.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Installation


## Running

To start a web server for the application, run:

    lein run

To test the apis type this in you browser:

#### To book a cab
    http://localhost:3000/api/fuber/book-cab/4/5/7/true
#### To start a trip
    http://localhost:3000/api/fuber/start-trip/4/5/7
#### To end a trip
    http://localhost:3000/api/fuber/end-trip/4/5/7

## Testing

To run the tests of the project, simply do:

    lein test


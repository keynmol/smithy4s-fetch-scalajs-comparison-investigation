http4s:
	scala-cli package test-http4s-dom.scala --js-mode release -f -o test-http4s.js --js-emit-source-maps

fetch:
	scala-cli package test-fetch.scala --js-mode release -f -o test-fetch.js --js-emit-source-maps

summarise: http4s fetch
	ls -lh *.js

cd ./fopdf
play deps --sync
play javadoc
play build-module

cd ../samples-and-tests/pdf-sample-app
play deps --sync
play ec
play run

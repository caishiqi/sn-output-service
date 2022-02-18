#!groovy

def serviceFolder() {
   return '.' // if the service folder empty then return '.' represent current dir
}

def serviceBuild() {
   dir('../'){sh 'mvn -U clean install -Dmaven.test.skip'}
}



def serviceDeploy() {
   echo 'none'
}

return this

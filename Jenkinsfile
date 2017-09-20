pipeline {
  agent any
  stages {
    stage('StageOne') {
      steps {
        mail(subject: 'test', body: 'echt jetzt?', from: 'me', to: 'burkhardt@web.de')
      }
    }
  }
}
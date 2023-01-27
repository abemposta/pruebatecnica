FROM jenkins/jenkins:lts

COPY src/jobs /usr/share/jenkins/ref/jobs
USER root
RUN chown -R jenkins:jenkins /usr/share/jenkins/ref/jobs/
USER jenkins

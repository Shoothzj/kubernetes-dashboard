FROM shoothzj/base:jdk17

ENV STATIC_PATH /opt/kubernetes-dashboard/static/

COPY dist /opt/kubernetes-dashboard

WORKDIR /opt/kubernetes-dashboard

EXPOSE 10003

CMD ["/usr/bin/dumb-init", "java", "-jar", "/opt/kubernetes-dashboard/kubernetes-dashboard.jar"]

## backend

临时readme，描述一下用到的东西。

- **.gitlab-ci.yml**：CI/CD

- **Dockerfile**：CI的test阶段用到的镜像。

- **代码风格检查工具**：pylint    pylint-django
  
  命令：pylint --load-plugins=pylint_django [包]
  
  \__init__.py是为了把整个项目作为一个包来检查代码风格。
  
- **为了通过代码风格测试，在开发时就得用用pylint了**

- **单元测试工具**：pytest    pytest-django

  在.gitlab-ci.yml文件中，单元测试的代码暂时注释掉了。

- **config/run.sh**：启动项目。但是我还没试过能不能行，后面有写项目了可以试试。。

- requirements.txt中写了需要用到的包，可以修改。requirements_dev.txt写了CI测试时要用到的包，在requirements.txt基础上加了pylint、pytest和coverage。

- sonar-project.properties

CD还没在软工平台测试过，不清楚能不能用，之后有了项目可以试试。


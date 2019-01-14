CREATE TABLE GITHUB_PROJECT(
  id        IDENTITY NOT NULL PRIMARY KEY,
  org_name  VARCHAR (50) NOT NULL,
  repo_name VARCHAR (50) NOT NULL UNIQUE
);

CREATE INDEX idx_repo_name ON GITHUB_PROJECT(repo_name);

INSERT INTO GITHUB_PROJECT(ORG_NAME,REPO_NAME) values ('spring-projects', 'spring-boot');
INSERT INTO GITHUB_PROJECT(ORG_NAME,REPO_NAME) values ('spring-io', 'initializr');
INSERT INTO GITHUB_PROJECT(ORG_NAME,REPO_NAME) values ('spring-io', 'sagan');
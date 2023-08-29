# change port
        "serve": {
          "builder": "@angular-devkit/build-angular:dev-server",
          "options": {
            "browserTarget": "angular-bootstrap-components:build",
            "port": 4201
          }

# How to Add Bootstrap to an Angular

## install the bootstrap and bootstrap-icons libraries
E:\Program Files\EclipseWorkspace\angular-bootstrap-components>npm install bootstrap bootstrap-icons
+ bootstrap-icons@1.10.5
+ bootstrap@5.3.1
added 2 packages from 2 contributors, removed 1 package and audited 1527 packages in 29.932s

## changes in angular.json
            "styles": [
              "src/styles.css",
              "node_modules/bootstrap/scss/bootstrap.scss",
              "node_modules/bootstrap-icons/font/bootstrap-icons.css"
            ],
            "scripts": [
              "node_modules/bootstrap/dist/js/bootstrap.bundle.min.js"
            ]

## install native support of bootstrap to angular

E:\Program Files\EclipseWorkspace\angular-bootstrap-components>ng add @ng-bootstrap/ng-bootstrap
Installing packages for tooling via npm.
Installed packages for tooling via npm.
UPDATE package.json (1358 bytes)
√ Packages installed successfully.
UPDATE angular.json (4107 bytes)
UPDATE src/polyfills.ts (3055 bytes)

--below is not good
E:\Program Files\EclipseWorkspace\angular-bootstrap-components>npm install @ng-bootstrap/ng-bootstrap@latest
+ @ng-bootstrap/ng-bootstrap@15.1.1
added 1 package from 1 contributor and audited 1528 packages in 26.063s

## import NgbModule in app.module.ts
  imports: [
    BrowserModule,
    AppRoutingModule,
    NgbModule
  ],

# bootstrap components documentation
https://getbootstrap.com/docs/5.3/getting-started/introduction/

# AngularBootstrapComponents

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 11.0.4.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI Overview and Command Reference](https://angular.io/cli) page.

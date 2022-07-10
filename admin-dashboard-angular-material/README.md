# AdminDashboardAngularMaterial

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 11.0.4.

##Important commands

* node --version
* ng --version

##To create this new angular project
E:\Program Files\EclipseWorkspace>ng new admin-dashboard-angular-material
? Do you want to enforce stricter type checking and stricter bundle budgets in the workspace?
  This setting helps improve maintainability and catch bugs ahead of time.
  For more information, see https://angular.io/strict Yes
? Would you like to add Angular routing? Yes
? Which stylesheet format would you like to use? SCSS   [ https://sass-lang.com/documentation/syntax#scss                ]
CREATE admin-dashboard-angular-material/angular.json (3913 bytes)
CREATE admin-dashboard-angular-material/package.json (1222 bytes)
CREATE admin-dashboard-angular-material/README.md (1038 bytes)
CREATE admin-dashboard-angular-material/tsconfig.json (737 bytes)
CREATE admin-dashboard-angular-material/tslint.json (3185 bytes)
CREATE admin-dashboard-angular-material/.editorconfig (274 bytes)
CREATE admin-dashboard-angular-material/.gitignore (631 bytes)
CREATE admin-dashboard-angular-material/.browserslistrc (703 bytes)
CREATE admin-dashboard-angular-material/karma.conf.js (1449 bytes)
CREATE admin-dashboard-angular-material/tsconfig.app.json (287 bytes)
CREATE admin-dashboard-angular-material/tsconfig.spec.json (333 bytes)
CREATE admin-dashboard-angular-material/src/favicon.ico (948 bytes)
CREATE admin-dashboard-angular-material/src/index.html (315 bytes)
CREATE admin-dashboard-angular-material/src/main.ts (372 bytes)
CREATE admin-dashboard-angular-material/src/polyfills.ts (2826 bytes)
CREATE admin-dashboard-angular-material/src/styles.scss (80 bytes)
CREATE admin-dashboard-angular-material/src/test.ts (753 bytes)
CREATE admin-dashboard-angular-material/src/assets/.gitkeep (0 bytes)
CREATE admin-dashboard-angular-material/src/environments/environment.prod.ts (51 bytes)
CREATE admin-dashboard-angular-material/src/environments/environment.ts (662 bytes)
CREATE admin-dashboard-angular-material/src/app/app-routing.module.ts (245 bytes)
CREATE admin-dashboard-angular-material/src/app/app.module.ts (393 bytes)
CREATE admin-dashboard-angular-material/src/app/app.component.html (25757 bytes)
CREATE admin-dashboard-angular-material/src/app/app.component.spec.ts (1135 bytes)
CREATE admin-dashboard-angular-material/src/app/app.component.ts (237 bytes)
CREATE admin-dashboard-angular-material/src/app/app.component.scss (0 bytes)
CREATE admin-dashboard-angular-material/e2e/protractor.conf.js (904 bytes)
CREATE admin-dashboard-angular-material/e2e/tsconfig.json (274 bytes)
CREATE admin-dashboard-angular-material/e2e/src/app.e2e-spec.ts (683 bytes)
CREATE admin-dashboard-angular-material/e2e/src/app.po.ts (274 bytes)
√ Packages installed successfully.
'git' is not recognized as an internal or external command,
operable program or batch file.

##To add angular material to this angular project
E:\Program Files\EclipseWorkspace\admin-dashboard-angular-material>ng add @angular/material
Installing packages for tooling via npm.
Installed packages for tooling via npm.
? Choose a prebuilt theme name, or "custom" for a custom theme: Indigo/Pink        [ Preview: https://material.angular.io?theme=indigo-pink ]
? Set up global Angular Material typography styles? Yes
? Set up browser animations for Angular Material? Yes
UPDATE package.json (1290 bytes)
√ Packages installed successfully.
UPDATE src/app/app.module.ts (502 bytes)
UPDATE angular.json (4077 bytes)
UPDATE src/index.html (597 bytes)
UPDATE src/styles.scss (181 bytes)

## Creating add product Dialog Component
E:\Program Files\EclipseWorkspace\admin-dashboard-angular-material>ng g c add-product-dialog
CREATE src/app/add-product-dialog/add-product-dialog.component.html (33 bytes)
CREATE src/app/add-product-dialog/add-product-dialog.component.spec.ts (698 bytes)
CREATE src/app/add-product-dialog/add-product-dialog.component.ts (322 bytes)
CREATE src/app/add-product-dialog/add-product-dialog.component.scss (0 bytes)
UPDATE src/app/app.module.ts (942 bytes)

## Creating Backend API service
E:\Program Files\EclipseWorkspace\admin-dashboard-angular-material>ng g s services/backend-api
CREATE src/app/services/backend-api.service.spec.ts (378 bytes)
CREATE src/app/services/backend-api.service.ts (139 bytes)

## How to install JSON server
E:\Program Files\EclipseWorkspace\admin-dashboard-angular-material>npm install -g json-server
C:\Users\Anuj\AppData\Roaming\npm\json-server -> C:\Users\Anuj\AppData\Roaming\npm\node_modules\json-server\lib\cli\bin.js
+ json-server@0.17.0
added 182 packages from 84 contributors in 63.277s

## How to create a fake JSON server instance
E:\Program Files\EclipseWorkspace\admin-dashboard-angular-material>json-server --watch db.json

  \{^_^}/ hi!

  Loading db.json
  Oops, db.json doesn't seem to exist
  Creating db.json with some default data

  Done

  Resources
  http://localhost:3000/posts
  http://localhost:3000/comments
  http://localhost:3000/profile

  Home
  http://localhost:3000

  Type s + enter at any time to create a snapshot of the database
  Watching...

by running above command a file db.json also created in project root path E:\Program Files\EclipseWorkspace\admin-dashboard-angular-material\db.json

## Usage of Angular pipes in this project
E:\Program Files\EclipseWorkspace\admin-dashboard-angular-material\src\app\app.component.html
<td mat-cell *matCellDef="let row"> {{row.productDate | date}} </td>
<td mat-cell *matCellDef="let row"> {{row.productPrice | currency}} </td>

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

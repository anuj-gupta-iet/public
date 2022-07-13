import { MatSnackBar } from '@angular/material/snack-bar';
import { ProductStatusSnackBarComponent } from '../product-status-snack-bar/product-status-snack-bar.component';

export class SnackBarUtil{
    public static logMsg(message: any) {
        console.log("Logging from Util class " + message);
    }

    //private _snackBar!: MatSnackBar;

    /*public static openProductSnackBar(message: any) {
        const _snackBar: MatSnackBar;
        _snackBar.openFromComponent(ProductStatusSnackBarComponent, {
            duration: 3000,//3 sec
            horizontalPosition: 'end',
            verticalPosition: 'top',
            data: message
        });
    }*/
}
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BackendApiService {

  constructor(private http: HttpClient) { }

  saveProduct(data: any){
    return this.http.post<any>("http://localhost:3000/productList/", data);
  }
  getAllProduct(){
    return this.http.get<any>("http://localhost:3000/productList/");
  }
}

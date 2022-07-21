import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class BackendApiService {

  constructor(private http: HttpClient) { }

  addProduct(data: any){
    return this.http.post<any>("http://localhost:3000/productList/", data);
  }
  updateProduct(id:number, data: any){
    return this.http.put<any>("http://localhost:3000/productList/"+id, data);
  }
  deleteProduct(id:number){
    return this.http.delete<any>("http://localhost:3000/productList/"+id);
  }
  getAllProduct(){
    return this.http.get<any>("http://localhost:3000/productList/");
  }
  getCountries(){
    return this.http.get<any>("http://localhost:3000/countryList/");
  }
}

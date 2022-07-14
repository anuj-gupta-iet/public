import { Component, OnInit, HostListener } from '@angular/core';
import { MatrimonyThumbImageComponent } from '../matrimony-thumb-image/matrimony-thumb-image.component';
import { ButtonRendererComponent } from '../renderer/button-renderer.component';

@Component({
  selector: 'app-matrimony',
  templateUrl: './matrimony.component.html',
  styleUrls: ['./matrimony.component.css']
})
export class MatrimonyComponent implements OnInit {
  private gridApi: any;
  private gridColumnApi: any;
  getRowHeight: any;
  screenWidth: number = 0;
  frameworkComponents: any;
  rowDataClicked1 = {};
  isGroomBtnClicked: boolean = false;
  isBrideBtnClicked: boolean = false;
  constructor() { 
    this.frameworkComponents = {
      matrimonyThumbImageComponent: MatrimonyThumbImageComponent,
    }
    this.getRowHeight = function (params:any) {
      return 100;
    };
  }

  ngOnInit(): void {
  }
  onGridReady(params: any) {
    this.gridApi = params.api;
    this.gridColumnApi = params.columnApi;
    params.api.sizeColumnsToFit();
    window.addEventListener('resize', function () {
      setTimeout(function () {
        params.api.sizeColumnsToFit();
      });
    });
    params.api.sizeColumnsToFit();
  }

  /* @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    console.log("hii");
    this.screenWidth = window.innerWidth;
    console.log("Width: "+this.screenWidth);
  } */

  @HostListener('window:resize', ['$event'])
  onGridSizeChanged(params:any) {
    this.screenWidth = window.innerWidth;
    console.log("screenWidth: "+ this.screenWidth);
    var columnsToShow = [];
    var columnsToHide = [];
    if(this.screenWidth < 450){
      columnsToHide.push("height");
      columnsToHide.push("gotra");
      columnsToHide.push("qualification");
      /* var allColumns = params.columnApi.getAllColumns();
      for (var i = 0; i < allColumns.length; i++) {
        console.log(allColumns[i]);
      } */
    } else {
      columnsToShow.push("height");
      columnsToShow.push("gotra");
      columnsToShow.push("qualification");
    }
    params.columnApi.setColumnsVisible(columnsToShow, true);
    params.columnApi.setColumnsVisible(columnsToHide, false);
    params.api.sizeColumnsToFit();
  }
  domLayout = 'autoHeight';
  gridOptions = {};
  columnDefs = [
    {
      headerName: '', minWidth: "120", maxWidth: "120",
      cellRenderer: 'matrimonyThumbImageComponent',
      cellRendererParams: {
        onClick: this.onBtnClick1.bind(this),
        renderingType: 'image'
      }
    },
    { headerName: "Serial Number", field: "srNo", sort: "desc", hide: true},
    { headerName: "Gender", field: "gender", hide: true},
    { headerName: "Name", field: "name", sortable: true, filter: true, resizable: true, wrapText: true},
    { headerName: "Date Of Birth", field: "dob", sortable: true, filter: true, resizable: true, wrapText: true},
    { headerName: "Birth Place", field: "birthPlace", sortable: true, filter: true, resizable: true, wrapText: true},
    { headerName: "Height", field: "height", sortable: true, filter: true, resizable: true},
    /*{ headerName: "Gotra", field: "gotra", sortable: true, filter: true, resizable: true},*/
    { headerName: "Qualification", field: "qualification", sortable: true, filter: true, resizable: true},
    /* {
      headerName: '', minWidth: "120", maxWidth: "120",
      cellRenderer: 'matrimonyThumbImageComponent',
      cellRendererParams: {
        onClick: this.onBtnClick1.bind(this),
        renderingType: 'button'
      }
    } */
  ];

  onBtnClick1(e:any) {
    this.rowDataClicked1 = e.rowData;
  }

  groomRowData = [
{"srNo":"344","name":"Akash Gupta","dob":"09/07/1989","birthPlace":"Rampur Uttar Pradesh","height":"5 ft 3 in","qualification":"M.B.A , B.B.A","gender":""},
{"srNo":"345","name":"Upwan Gupta","dob":"01/01/1991","birthPlace":"Firozabad","height":"5.2","qualification":"B.com Final","gender":""},
{"srNo":"346","name":"Amar Gupta","dob":"22/05/1993","birthPlace":"Agra","height":"5.5","qualification":"B.Tech (CS)","gender":""},
    {"srNo":"343","name":"Sachin Gupta","dob":"04/02/1994","birthPlace":"Not Available","height":"5'5''","qualification":"B.E.","gender":"M"},
    {"srNo":"341","name":"Abhishek Gupta","dob":"13/03/1991","birthPlace":"Agra","height":"5\u00274\"","qualification":"B.tech","gender":"M"},
    { srNo: "339", name: "Ashish Gupta", dob: "06/05/1995", birthPlace: "Agra", height: "5'8''", qualification: "B.Tech.", gender: "M" },
    { srNo: "201", name: "Sajal Parolia", dob: "29/04/1989", birthPlace: "Firozabad", height: "5'4''", gotra: "Malik, Mama", qualification: "MBA", gender: "M" },
    { srNo: "202", name: "Manan Prafulbhai Gupta", dob: "19/02/1992", birthPlace: "Satlasana", height: "5'11''", gotra: "Soni, Chosaiya", qualification: "BE", gender: "M" },
    { srNo: "203", name: "Kushal Gupta", dob: "03/08/1991", birthPlace: "Firozabad", height: "5'9''", gotra: "Malik, Mama", qualification: "B.Sc", gender: "M" },
    { srNo: "204", name: "Bhargav Lalaji", dob: "27/03/1992", birthPlace: "Sevaliya", height: "5'3''", gotra: "Chaudarana, Pachadhari", qualification: "ME", gender: "M" },
    { srNo: "205", name: "Dr. Arun Kumar Gupta", dob: "24/09/1989", birthPlace: "Pinahat", height: "5'4''", gotra: "Malik, Mama", qualification: "MBBS", gender: "M" },
    { srNo: "206", name: "Shivam Gupta", dob: "12/05/1997", birthPlace: "Jaswant Nagar", height: "5'6''", gotra: "", qualification: "B.Tech.", gender: "M" },
    { srNo: "306", name: "Siddhant Modi", dob: "19/05/1992", birthPlace: "Agra", height: "5'4''", qualification: "B.Tech.", gender: "M" },
    { srNo: "307", name: "Tarun Gupta (Manglik)", dob: "13/05/1993", birthPlace: "Kanpur", height: "5'4''", qualification: "B.B.A.", gender: "M" },
    { srNo: "308", name: "Aniket Anvaria", dob: "08/08/1992", birthPlace: "Agra", height: "5'10''", qualification: "B.Tech.", gender: "M" },
    { srNo: "309", name: "Arpit Gupta", dob: "06/12/1993", birthPlace: "Agra", height: "5'8''", qualification: "B.Com.", gender: "M" },
    { srNo: "310", name: "Aman Gupta (Manglik)", dob: "01/07/1992", birthPlace: "Agra", height: "5'9''", qualification: "B.Tech.", gender: "M" },
    { srNo: "311", name: "Nirdosh Surendra Gupta", dob: "02/10/1992", birthPlace: "Palanpur", height: "6'1''", qualification: "B.Tech.", gender: "M" },
    { srNo: "312", name: "Dr. Atul Kumar Gupta (Manglik)", dob: "20/03/1984", birthPlace: "Mehsana", height: "5'11''", qualification: "MBBS", gender: "M" },
    { srNo: "313", name: "Lavish Gupta", dob: "01/09/1990", birthPlace: "Agra", height: "5'7''", qualification: "MBA", gender: "M" },
    { srNo: "314", name: "Vedant Raj", dob: "06/08/1991", birthPlace: "Panwariya", height: "5'5''", qualification: "B.Tech.", gender: "M" },
    { srNo: "315", name: "Masoom Gupta", dob: "17/05/1991", birthPlace: "Morena", height: "5'8''", qualification: "B.E.", gender: "M" },
    { srNo: "316", name: "Rehanshu Gupta", dob: "11/10/1991", birthPlace: "Agra", height: "5'6''", qualification: "B.Com.", gender: "M" },
    { srNo: "317", name: "Ankur Gupta", dob: "17/08/1991", birthPlace: "Agra", height: "5'5''", qualification: "M.S.", gender: "M" },
    { srNo: "318", name: "Aman Gupta", dob: "02/02/1993", birthPlace: "Muzaffarpure", height: "5'6''", qualification: "B.Com.", gender: "M" },
    { srNo: "319", name: "Krishnesh Gupta", dob: "24/08/1989", birthPlace: "Agra", height: "5'1''", qualification: "B.E.", gender: "M" },
    { srNo: "321", name: "Balkishan Gupta", dob: "14/08/1990", birthPlace: "Agra", height: "", qualification: "B.Sc.", gender: "M" },
    { srNo: "322", name: "Rituraj Gupta", dob: "05/02/1991", birthPlace: "Allahabad", height: "5'7''", qualification: "M.B.A.", gender: "M" },
    { srNo: "323", name: "Supath Gupta", dob: "15/12/1992", birthPlace: "Ahemdabad", height: "5'3''", qualification: "B.E.", gender: "M" },
    { srNo: "324", name: "Sagar Gupta", dob: "13/11/1991", birthPlace: "Agra", height: "5'4''", qualification: "M.B.A.", gender: "M" },
    { srNo: "325", name: "Dr. Abhishek Gupta", dob: "15/04/1985", birthPlace: "Agra", height: "5'3''", qualification: "MBBS", gender: "M" },
    { srNo: "326", name: "Vipul Gupta", dob: "20/11/1987", birthPlace: "Kolkata", height: "5'5''", qualification: "Phd", gender: "M" },
    { srNo: "327", name: "CA Vishal Gupta", dob: "20/09/1990", birthPlace: "Firozabad", height: "5'4''", qualification: "B.Com.", gender: "M" },
    { srNo: "328", name: "Mohit Gupta", dob: "02/12/1988", birthPlace: "Agra", height: "5'9''", qualification: "B.Tech.", gender: "M" },
    { srNo: "329", name: "Amit Kumar Gupta", dob: "03/01/1988", birthPlace: "Agra", height: "5'9''", qualification: "M.Tech.", gender: "M" },
    { srNo: "330", name: "Rinkal Gupta", dob: "25/01/1990", birthPlace: "Agra", height: "5'8''", qualification: "M.Com.", gender: "M" },
    { srNo: "331", name: "Anuj Gupta", dob: "16/02/1991", birthPlace: "Agra", height: "5'5''", qualification: "B.Com.", gender: "M" },
    { srNo: "332", name: "Deepanshu Gupta", dob: "13/11/1993", birthPlace: "Kanpur", height: "5'6''", qualification: "B.Tech.", gender: "M" },
    { srNo: "335", name: "Robin Naugaiya", dob: "13/07/1987", birthPlace: "Kanpur", height: "5'7''", qualification: "B.Com.", gender: "M" },
    { srNo: "336", name: "Ajay Gupta", dob: "11/8/1990", birthPlace: "Porsa", height: "6'", qualification: "B.E.", gender: "M" },
    { srNo: "338", name: "Kushal Gupta", dob: "20/12/1994", birthPlace: "Kota Rajasthan", height: "5'10''", qualification: "B.Tech.", gender: "M" },

  ];

  brideRowData = [
    {"srNo":"342","name":"Kinjal Gupta","dob":"04/05/1995","birthPlace":"Agra","height":"5\u00272\u0027\u0027","qualification":"BBA, MBA (Finance and marketing)","gender":"F"},
    { srNo: "340", name: "Niharika Gupta", dob: "09/09/1989", birthPlace: "Agra", height: "5'3''", qualification: "MBA", gender: "F" },                 
    { srNo: "301", name: "Prachi Gupta", dob: "14/11/1990", birthPlace: "Agra", height: "5'2''", gotra: "Arvariya", qualification: "MBA", gender: "F" },
    { srNo: "302", name: "Nishu Gupta", dob: "14/09/1988", birthPlace: "Agra", height: "5'3''", gotra: "Arvariya", qualification: "MBA", gender: "F" },
    { srNo: "303", name: "Dr. Pragya Gupta", dob: "23/01/1994", birthPlace: "Agra", height: "5'2''", gotra: "Arvariya", qualification: "BDS", gender: "F" },
    { srNo: "304", name: "Priyanshi Gupta", dob: "25/09/1992", birthPlace: "Firozabad", height: "5'1''", gotra: "Bachharwar", qualification: "B.Des.", gender: "F" },
    { srNo: "305", name: "Dr. Shivani Gupta", dob: "19/11/1992", birthPlace: "Agra", height: "5'3''", gotra: "Mohaniya", qualification: "BHMS", gender: "F" },
    { srNo: "320", name: "Kalpana Gupta (Manglik)", dob: "13/12/1990", birthPlace: "Pinahat", height: "5'", qualification: "M.Sc.", gender: "F" },
    { srNo: "195", name: "Asha Gupta (Manglik)", dob: "16/05/1991", birthPlace: "Agra", height: "5'4''", qualification: "LLB", gender: "F" },
    { srNo: "196", name: "Suruchi Gupta", dob: "23/10/1992", birthPlace: "Agra", height: "5'", qualification: "MBA", gender: "F" },
    { srNo: "197", name: "CA Ayushi Gupta", dob: "20/05/1994", birthPlace: "Muzaffarnagar", height: "5'", qualification: "B.Com", gender: "F" },
    { srNo: "198", name: "Nikita Gupta", dob: "30/11/1992", birthPlace: "Delhi", height: "5'4''", qualification: "Masters in Journalism", gender: "F" },
    { srNo: "333", name: "Shruti Gupta", dob: "29/10/1995", birthPlace: "Kanpur", height: "5'3''", qualification: "B.Tech.", gender: "F" },
    { srNo: "334", name: "Charu Gupta", dob: "29/08/1991", birthPlace: "Jaipur", height: "5'4''", qualification: "B.TextileDesign", gender: "F" },
    { srNo: "337", name: "Surabhi Gupta", dob: "17/02/1993", birthPlace: "Bareilly", height: "5'4''", qualification: "B.Tech.", gender: "F" },
  ];

  onGroomBtnClick() {
    console.log("Groom Button Clicked");
    this.isGroomBtnClicked = true;
    this.isBrideBtnClicked = false;
  }

  onBrideBtnClick() {
    console.log("Bride Button Clicked");
    console.log("Groom Button Clicked");
    this.isGroomBtnClicked = false;
    this.isBrideBtnClicked = true;
  }
}

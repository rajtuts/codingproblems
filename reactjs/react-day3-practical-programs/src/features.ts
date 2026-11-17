import {createSlice,createAsyncThunk, type PayloadAction} from "@reduxjs/toolkit";
export interface Employee {id:number; name:string; department:string}
interface EmployeesState {items:Employee[]}
const initial:EmployeesState={items:[{id:1,name:"Asha",department:"Engineering"},{id:2,name:"Ravi",department:"Finance"},{id:3,name:"Maya",department:"Operations"}]};
const slice=createSlice({name:"employees",initialState:initial,reducers:{
 addEmployee:(s,a:PayloadAction<Employee>)=>{s.items.push(a.payload)},
 deleteEmployee:(s,a:PayloadAction<number>)=>{s.items=s.items.filter(e=>e.id!==a.payload)},
 updateEmployee:(s,a:PayloadAction<Employee>)=>{const i=s.items.findIndex(e=>e.id===a.payload.id);if(i>=0)s.items[i]=a.payload}
}});
export const {addEmployee,deleteEmployee,updateEmployee}=slice.actions;
export const employeeReducer=slice.reducer;
export const fetchEmployees=createAsyncThunk<Employee[],boolean|undefined,{rejectValue:string}>("employees/fetch",async(fail,{rejectWithValue,signal})=>{
 await new Promise<void>((resolve,reject)=>{const t=window.setTimeout(resolve,700);signal.addEventListener("abort",()=>{window.clearTimeout(t);reject(new DOMException("Aborted","AbortError"))},{once:true})});
 if(fail)return rejectWithValue("The employee API returned an error.");
 // Replace this mock with fetch('http://localhost:8080/api/employees',{signal}) for Spring Boot.
 return [{id:101,name:"Jordan",department:"Technology"},{id:102,name:"Priya",department:"Risk"}];
});
interface ApiState {items:Employee[];loading:boolean;error:string|null}
const apiSlice=createSlice({name:"employeeApi",initialState:{items:[],loading:false,error:null} as ApiState,reducers:{},extraReducers:b=>b
 .addCase(fetchEmployees.pending,s=>{s.loading=true;s.error=null})
 .addCase(fetchEmployees.fulfilled,(s,a)=>{s.loading=false;s.items=a.payload})
 .addCase(fetchEmployees.rejected,(s,a)=>{s.loading=false;s.error=a.payload??a.error.message??"Request failed"})
});
export const apiReducer=apiSlice.reducer;
const authSlice=createSlice({name:"auth",initialState:{user:null as null|{username:string;role:string}},reducers:{
 login:(s,a:PayloadAction<{username:string;role:string}>)=>{s.user=a.payload},
 logout:s=>{s.user=null}
}});
export const {login,logout}=authSlice.actions;
export const authReducer=authSlice.reducer;
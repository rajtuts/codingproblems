import {configureStore,type Middleware} from "@reduxjs/toolkit";import {employeeReducer,apiReducer,authReducer} from "../features";
const logger:Middleware=store=>next=>action=>{console.log("[middleware] action",action);const result=next(action);console.log("[middleware] state",store.getState());return result};
export const store=configureStore({reducer:{employees:employeeReducer,employeeApi:apiReducer,auth:authReducer},middleware:g=>g().concat(logger)});
export type RootState=ReturnType<typeof store.getState>;export type AppDispatch=typeof store.dispatch;
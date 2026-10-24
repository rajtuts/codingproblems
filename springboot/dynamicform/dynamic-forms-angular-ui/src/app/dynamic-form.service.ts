import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { FormDefinition, SubmissionResponse } from './form.models';

@Injectable({ providedIn: 'root' })
export class DynamicFormService {
  private readonly http = inject(HttpClient);
  // Angular dev server proxies /api to Spring Boot (see proxy.conf.json).
  private readonly apiUrl = '/api';

  getForm(code: string): Observable<FormDefinition> {
    return this.http.get<FormDefinition>(`${this.apiUrl}/forms/${encodeURIComponent(code)}`);
  }

  submitForm(code: string, values: Record<string, unknown>): Observable<SubmissionResponse> {
    return this.http.post<SubmissionResponse>(
      `${this.apiUrl}/forms/${encodeURIComponent(code)}/submissions`,
      { values }
    );
  }

  getSubmission(id: number): Observable<SubmissionResponse> {
    return this.http.get<SubmissionResponse>(`${this.apiUrl}/submissions/${id}`);
  }

  listSubmissions(code: string): Observable<SubmissionResponse[]> {
    return this.http.get<SubmissionResponse[]>(
      `${this.apiUrl}/forms/${encodeURIComponent(code)}/submissions`
    );
  }
}
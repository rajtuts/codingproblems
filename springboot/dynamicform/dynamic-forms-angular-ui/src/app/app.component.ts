import { CommonModule } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import {
  AbstractControl, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators
} from '@angular/forms';
import { HttpErrorResponse } from '@angular/common/http';
import { DynamicFormService } from './dynamic-form.service';
import { FieldDefinition, FormDefinition, SubmissionResponse } from './form.models';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {
  private readonly api = inject(DynamicFormService);

  formCode = 'employee';
  definition: FormDefinition | null = null;
  dynamicForm = new FormGroup({});
  loading = false;
  submitting = false;
  errorMessage = '';
  successMessage = '';
  lastSubmission: SubmissionResponse | null = null;
  savedSubmissions: SubmissionResponse[] = [];

  ngOnInit(): void {
    this.loadForm();
  }

  get fields(): FieldDefinition[] {
    return this.definition?.fields ?? [];
  }

  loadForm(): void {
    const code = this.formCode.trim();
    if (!code) {
      this.errorMessage = 'Enter a form code.';
      return;
    }

    this.loading = true;
    this.errorMessage = '';
    this.successMessage = '';
    this.lastSubmission = null;
    this.api.getForm(code).subscribe({
      next: definition => {
        this.definition = definition;
        const controls: Record<string, AbstractControl> = {};

        for (const field of definition.fields) {
          const initialValue = field.defaultValue ?? (field.type.toUpperCase() === 'CHECKBOX' ? false : '');
          const validators = field.required ? [Validators.required] : [];

          // Read-only controls are disabled in the UI. The server remains authoritative.
          controls[field.key] = new FormControl(
            { value: initialValue, disabled: field.readOnly },
            validators
          );
        }

        this.dynamicForm = new FormGroup(controls);
        this.loading = false;
        this.loadSubmissions();
      },
      error: err => {
        this.loading = false;
        this.definition = null;
        this.dynamicForm = new FormGroup({});
        this.errorMessage = this.errorText(err, 'Could not load form. Check the form code and Spring Boot server.');
      }
    });
  }

  loadSubmissions(): void {
    if (!this.definition) return;
    this.api.listSubmissions(this.definition.code).subscribe({
      next: rows => this.savedSubmissions = rows,
      error: () => this.savedSubmissions = []
    });
  }

  isChoiceField(field: FieldDefinition): boolean {
    return ['DROPDOWN', 'RADIO'].includes(field.type.toUpperCase());
  }

  optionsFor(field: FieldDefinition): string[] {
    if (!field.optionsJson) return [];
    try {
      const parsed: unknown = JSON.parse(field.optionsJson);
      return Array.isArray(parsed) ? parsed.filter((x): x is string => typeof x === 'string') : [];
    } catch {
      return [];
    }
  }

  control(field: FieldDefinition): AbstractControl {
    return this.dynamicForm.get(field.key)!;
  }

  submit(): void {
    if (!this.definition || this.submitting) return;

    this.successMessage = '';
    this.errorMessage = '';
    this.lastSubmission = null;

    this.dynamicForm.markAllAsTouched();
    if (this.dynamicForm.invalid) {
      this.errorMessage = 'Please correct the required fields before submitting.';
      return;
    }

    // getRawValue includes disabled/read-only fields; the backend must still enforce these.
    const values = this.dynamicForm.getRawValue() as Record<string, unknown>;
    this.submitting = true;

    this.api.submitForm(this.definition.code, values).subscribe({
      next: response => {
        this.lastSubmission = response;
        this.successMessage = `Form submitted successfully. Submission ID: ${response.id}`;
        this.submitting = false;
        this.loadSubmissions();
      },
      error: err => {
        this.errorMessage = this.errorText(err, 'Submission failed.');
        this.submitting = false;
      }
    });
  }

  viewSubmission(id: number): void {
    this.errorMessage = '';
    this.successMessage = '';
    this.api.getSubmission(id).subscribe({
      next: response => {
        this.lastSubmission = response;
        this.successMessage = `Loaded submission ${response.id}.`;
        if (this.definition) {
          for (const field of this.fields) {
            const saved = response.values[field.key];
            if (saved !== undefined && !field.readOnly) {
              this.control(field).setValue(saved);
            }
          }
        }
      },
      error: err => this.errorMessage = this.errorText(err, 'Could not load submission.')
    });
  }

  resetForm(): void {
    for (const field of this.fields) {
      const initialValue = field.defaultValue ?? (field.type.toUpperCase() === 'CHECKBOX' ? false : '');
      this.control(field).setValue(initialValue);
      this.control(field).markAsPristine();
      this.control(field).markAsUntouched();
    }
    this.lastSubmission = null;
    this.successMessage = '';
    this.errorMessage = '';
  }

  private errorText(err: HttpErrorResponse, fallback: string): string {
    const message = err.error?.detail ?? err.error?.message ?? err.error?.error;
    return typeof message === 'string' ? message : fallback;
  }
}
export interface FieldDefinition {
  key: string;
  label: string;
  type: string;
  required: boolean;
  readOnly: boolean;
  optionsJson: string | null;
  defaultValue: string | null;
  sortOrder: number;
}

export interface FormDefinition {
  id: number;
  code: string;
  title: string;
  fields: FieldDefinition[];
}

export interface SubmissionResponse {
  id: number;
  formCode: string;
  submittedAt: string;
  values: Record<string, unknown>;
}
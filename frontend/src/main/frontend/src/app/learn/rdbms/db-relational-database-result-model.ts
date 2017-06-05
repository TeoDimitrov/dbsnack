import {QueryResultModel} from "./db-query-result-model";
import {ColumnMetadataModel} from "./db-column-metadata-model";

export class RelationalDatabaseResultModel {

  queryResult: QueryResultModel;

  tablesMetaData: Map<string, ColumnMetadataModel[]>;

  correct: boolean;

  message: string;
}

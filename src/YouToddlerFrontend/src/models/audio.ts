/* tslint:disable */
/* eslint-disable */
/**
 * YouToddler - OpenAPI 3.0
 * This is an on-premise download manager for youtube-dlp. It should be based on the OpenAPI 3.0 specification.   _If you're looking for the sprint board, then click [here](https://manhatten.atlassian.net/jira/software/projects/MAN/boards/1)._  Some useful links: - [The YouToddler repository](https://github.com/cant0r/YouToddler) - [The backend API definition for YouToddler](https://manhatten.atlassian.net/wiki/spaces/AT/pages/6094879/YouToddler+Backend) - [The WebAPi definition for YouToddler](https://manhatten.atlassian.net/wiki/spaces/AT/pages/4063276/REST+API+interfaces)
 *
 * OpenAPI spec version: 0.2.1
 * Contact: pixelbetyar@mailbox.unideb.hu
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
/**
 * 
 * @export
 * @interface Audio
 */
export interface Audio {
    /**
     * 
     * @type {string}
     * @memberof Audio
     */
    audioCodec: string;
    /**
     * 
     * @type {number}
     * @memberof Audio
     */
    abr?: number;
}
import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './video-file.reducer';
import { IVideoFile } from 'app/shared/model/video-file.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVideoFileProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class VideoFile extends React.Component<IVideoFileProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { videoFileList, match } = this.props;
    return (
      <div>
        <h2 id="video-file-heading">
          Video Files
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Video File
          </Link>
        </h2>
        <div className="table-responsive">
          {videoFileList && videoFileList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Uuid</th>
                  <th>Size</th>
                  <th>Mime Type</th>
                  <th>Start Date</th>
                  <th>End Date</th>
                  <th>Stand</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {videoFileList.map((videoFile, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${videoFile.id}`} color="link" size="sm">
                        {videoFile.id}
                      </Button>
                    </td>
                    <td>{videoFile.uuid}</td>
                    <td>{videoFile.size}</td>
                    <td>{videoFile.mimeType}</td>
                    <td>
                      <TextFormat type="date" value={videoFile.startDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={videoFile.endDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{videoFile.stand ? <Link to={`stand/${videoFile.stand.id}`}>{videoFile.stand.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${videoFile.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${videoFile.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${videoFile.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Video Files found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ videoFile }: IRootState) => ({
  videoFileList: videoFile.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VideoFile);

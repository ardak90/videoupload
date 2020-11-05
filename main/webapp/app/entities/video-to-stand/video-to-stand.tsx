import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './video-to-stand.reducer';
import { IVideoToStand } from 'app/shared/model/video-to-stand.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVideoToStandProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class VideoToStand extends React.Component<IVideoToStandProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { videoToStandList, match } = this.props;
    return (
      <div>
        <h2 id="video-to-stand-heading">
          Video To Stands
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Video To Stand
          </Link>
        </h2>
        <div className="table-responsive">
          {videoToStandList && videoToStandList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Video File</th>
                  <th>Stand</th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {videoToStandList.map((videoToStand, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${videoToStand.id}`} color="link" size="sm">
                        {videoToStand.id}
                      </Button>
                    </td>
                    <td>
                      {videoToStand.videoFile ? (
                        <Link to={`video-file/${videoToStand.videoFile.id}`}>{videoToStand.videoFile.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{videoToStand.stand ? <Link to={`stand/${videoToStand.stand.id}`}>{videoToStand.stand.id}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${videoToStand.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${videoToStand.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${videoToStand.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            <div className="alert alert-warning">No Video To Stands found</div>
          )}
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ videoToStand }: IRootState) => ({
  videoToStandList: videoToStand.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VideoToStand);

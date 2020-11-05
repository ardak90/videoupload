import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './video-file.reducer';
import { IVideoFile } from 'app/shared/model/video-file.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVideoFileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VideoFileDetail extends React.Component<IVideoFileDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { videoFileEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            VideoFile [<b>{videoFileEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="uuid">Uuid</span>
            </dt>
            <dd>{videoFileEntity.uuid}</dd>
            <dt>
              <span id="size">Size</span>
            </dt>
            <dd>{videoFileEntity.size}</dd>
            <dt>
              <span id="mimeType">Mime Type</span>
            </dt>
            <dd>{videoFileEntity.mimeType}</dd>
            <dt>
              <span id="startDate">Start Date</span>
            </dt>
            <dd>
              <TextFormat value={videoFileEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">End Date</span>
            </dt>
            <dd>
              <TextFormat value={videoFileEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>Stand</dt>
            <dd>{videoFileEntity.stand ? videoFileEntity.stand.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/video-file" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/video-file/${videoFileEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ videoFile }: IRootState) => ({
  videoFileEntity: videoFile.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VideoFileDetail);

import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './video-to-stand.reducer';
import { IVideoToStand } from 'app/shared/model/video-to-stand.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IVideoToStandDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class VideoToStandDetail extends React.Component<IVideoToStandDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { videoToStandEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            VideoToStand [<b>{videoToStandEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>Video File</dt>
            <dd>{videoToStandEntity.videoFile ? videoToStandEntity.videoFile.id : ''}</dd>
            <dt>Stand</dt>
            <dd>{videoToStandEntity.stand ? videoToStandEntity.stand.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/video-to-stand" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/video-to-stand/${videoToStandEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ videoToStand }: IRootState) => ({
  videoToStandEntity: videoToStand.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(VideoToStandDetail);
